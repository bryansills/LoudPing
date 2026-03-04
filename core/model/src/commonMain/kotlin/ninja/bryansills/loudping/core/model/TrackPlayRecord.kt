package ninja.bryansills.loudping.core.model

import kotlin.time.Instant

data class TrackPlayRecord(
    val track: Track,
    val timestamp: Instant,
    val context: TrackPlayContext,
)
