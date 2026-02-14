package ninja.bryansills.loudping.core.model.test

import kotlin.time.Duration
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.core.model.Track
import ninja.bryansills.loudping.core.model.TrackAlbum
import ninja.bryansills.loudping.core.model.TrackPlayContext
import ninja.bryansills.loudping.core.model.TrackPlayRecord

fun testRecord(
    index: Int = 0,
    trackId: String = "TRACK-ID-$index",
    trackTitle: String = "TRACK TITLE $index",
    albumId: String = "ALBUM-ID-$index",
    timestamp: Instant = Clock.System.now(),
): TrackPlayRecord = TrackPlayRecord(
    track = Track(
        spotifyId = trackId,
        title = trackTitle,
        trackNumber = -1,
        discNumber = -1,
        duration = Duration.ZERO,
        album = TrackAlbum(
            spotifyId = albumId,
            title = "",
            trackCount = -1,
            coverImage = null,
        ),
        artists = listOf(),
    ),
    timestamp = timestamp,
    context = TrackPlayContext.Unknown,
)
