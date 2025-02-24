package ninja.bryansills.loudping.grouper

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.flowOf
import ninja.bryansills.loudping.core.model.test.TimestampTicker
import ninja.bryansills.loudping.core.model.test.runTestTurbine
import ninja.bryansills.loudping.core.model.test.testRecord

class DefaultAlbumGrouperTest {
    private val timestampTicker = TimestampTicker()

    @Test
    fun `simplest example`() = runTestTurbine {
        val album1 = (0..10).map { testRecord(index = it, albumId = "ALBUM001", timestamp = timestampTicker.next) }
        val album2 = (0..8).map { testRecord(index = it, albumId = "ALBUM999", timestamp = timestampTicker.next) }
        val input = flowOf(album1 + album2)

        val turbine = DefaultAlbumGrouper().invoke(input).testIn(backgroundScope)

        assertEquals(album1, turbine.awaitItem().supposedAlbum)
        turbine.awaitComplete()
    }
}
