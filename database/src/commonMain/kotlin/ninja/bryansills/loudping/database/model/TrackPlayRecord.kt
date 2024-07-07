package ninja.bryansills.loudping.database.model

import kotlinx.datetime.Instant

data class TrackPlayRecord(
    val trackId: String,
    val trackNumber: Int,
    val trackTitle: String,
    val albumId: String,
    val timestamp: Instant,
)
