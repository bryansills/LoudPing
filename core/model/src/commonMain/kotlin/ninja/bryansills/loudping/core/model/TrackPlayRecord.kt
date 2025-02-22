package ninja.bryansills.loudping.core.model

import kotlinx.datetime.Instant

data class TrackPlayRecord(
    val track: Track,
    val timestamp: Instant,
    val context: TrackPlayContext,
)
