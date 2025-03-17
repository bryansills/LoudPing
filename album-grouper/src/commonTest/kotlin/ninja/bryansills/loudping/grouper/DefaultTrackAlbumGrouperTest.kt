package ninja.bryansills.loudping.grouper

import app.cash.turbine.Event
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ninja.bryansills.loudping.core.model.test.testRecord
import ninja.bryansills.loudping.core.test.TimestampTicker
import ninja.bryansills.loudping.core.test.runTestTurbine

class DefaultTrackAlbumGrouperTest {
    private val timestampTicker = TimestampTicker()

    @Test
    fun `simplest example`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2 = (1..8).map { testRecord(index = it, albumId = "ignored", timestamp = timestampTicker.next) }
        val input = flowOf(album1 + album2)

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)

        assertEquals(album1, turbine.awaitItem().supposedAlbum)
        turbine.cancelAndIgnoreRemainingEvents()
    }

    @Test
    fun `completes after finishing processing input`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2 = (1..8).map { testRecord(index = it, albumId = "ignored", timestamp = timestampTicker.next) }
        val input = flowOf(album1 + album2)

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)

        assertEquals(album1, turbine.awaitItem().supposedAlbum)
        turbine.awaitComplete()
        turbine.ensureAllEventsConsumed()
    }

    @Test
    fun `does not emit the last album`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2 = (1..9).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album3 = (1..20).map { testRecord(index = it, albumId = "not-emitted", timestamp = timestampTicker.next) }
        val input = flowOf(album1 + album2 + album3)

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)

        assertEquals(album1, turbine.awaitItem().supposedAlbum)
        assertEquals(album2, turbine.awaitItem().supposedAlbum)
        turbine.awaitComplete()
        turbine.ensureAllEventsConsumed()
    }

    @Test
    fun `works over multiple emissions of input stream`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2 = (1..9).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album3 = (1..20).map { testRecord(index = it, albumId = "not-emitted", timestamp = timestampTicker.next) }

        val input = flow {
            emit(album1)
            emit(album2)
            emit(album3)
        }

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)

        assertEquals(album1, turbine.awaitItem().supposedAlbum)
        assertEquals(album2, turbine.awaitItem().supposedAlbum)
        turbine.cancelAndIgnoreRemainingEvents()
    }

    @Test
    fun `single album can be spread over 2 emissions`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2chunk1 = (1..9).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album2chunk2 = (10..19).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album3 = (1..20).map { testRecord(index = it, albumId = "not-emitted", timestamp = timestampTicker.next) }

        val input = flow {
            emit(album1 + album2chunk1)
            emit(album2chunk2)
            emit(album3)
        }

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)

        assertEquals(album1, turbine.awaitItem().supposedAlbum)
        val album2 = album2chunk1 + album2chunk2
        assertEquals(album2, turbine.awaitItem().supposedAlbum)
        turbine.cancelAndIgnoreRemainingEvents()
    }

    @Test
    fun `single album can be spread over multiple emissions`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2chunk1 = (1..9).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album2chunk2 = (10..17).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album2chunk3 = (18..29).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album3 = (1..20).map { testRecord(index = it, albumId = "not-emitted", timestamp = timestampTicker.next) }

        val input = flow {
            emit(album1 + album2chunk1)
            emit(album2chunk2)
            emit(album2chunk3 + album3)
        }

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)

        assertEquals(album1, turbine.awaitItem().supposedAlbum)
        val album2 = album2chunk1 + album2chunk2 + album2chunk3
        assertEquals(album2, turbine.awaitItem().supposedAlbum)
        turbine.cancelAndIgnoreRemainingEvents()
    }

    @Test
    fun `can be single track albums`() = runTestTurbine {
        val singleTrackAlbums = (1..8).map { testRecord(index = 1, albumId = "ALBUM00$it", timestamp = timestampTicker.next) }

        val input = flow {
            singleTrackAlbums.forEach { emit(listOf(it)) }
        }

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)
        val turbineEvents = turbine.cancelAndConsumeRemainingEvents()

        singleTrackAlbums
            // remember: the last album is not emitted
            .subList(0, singleTrackAlbums.size - 1)
            .zip(turbineEvents)
            .forEach { (expectedAlbum, turbineEvent) ->
                assertEquals(listOf(expectedAlbum), (turbineEvent as Event.Item).value.supposedAlbum)
            }
    }

    @Test
    fun `keeps track about what was played before`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2 = (1..9).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album3 = (1..20).map { testRecord(index = it, albumId = "ALBUM003", timestamp = timestampTicker.next) }
        val album4 = (1..20).map { testRecord(index = it, albumId = "not-emitted", timestamp = timestampTicker.next) }

        val input = flow {
            emit(album1)
            emit(album2)
            emit(album3)
            emit(album4)
        }

        val previousTrackCount = 3
        val turbine = DefaultAlbumGrouper(previousTrackCount = previousTrackCount).invoke(input).testIn(backgroundScope)

        assertEquals(listOf(), turbine.awaitItem().previousSongs)
        assertEquals(album1.takeLast(previousTrackCount), turbine.awaitItem().previousSongs)
        assertEquals(album2.takeLast(previousTrackCount), turbine.awaitItem().previousSongs)
        turbine.cancelAndIgnoreRemainingEvents()
    }

    @Test
    fun `keeps track about what was played after`() = runTestTurbine {
        val album1 = (1..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2 = (1..9).map { testRecord(index = it, albumId = "ALBUM002", timestamp = timestampTicker.next) }
        val album3 = (1..20).map { testRecord(index = it, albumId = "ALBUM003", timestamp = timestampTicker.next) }
        val album4 = (1..20).map { testRecord(index = it, albumId = "not-emitted", timestamp = timestampTicker.next) }

        val input = flow {
            emit(album1)
            emit(album2)
            emit(album3)
            emit(album4)
        }

        val followingTrackCount = 4
        val turbine = DefaultAlbumGrouper(
            followingTrackCount = followingTrackCount,
        ).invoke(input).testIn(backgroundScope)

        assertEquals(album2.take(followingTrackCount), turbine.awaitItem().subsequentSongs)
        assertEquals(album3.take(followingTrackCount), turbine.awaitItem().subsequentSongs)
        assertEquals(album4.take(followingTrackCount), turbine.awaitItem().subsequentSongs)
        turbine.cancelAndIgnoreRemainingEvents()
    }
}
