package ninja.bryansills.loudping.database.model

import kotlinx.datetime.Instant

data class TrackPlayRecord(
    val track: Track,
    val timestamp: Instant,
    val context: TrackPlayContext,
)
