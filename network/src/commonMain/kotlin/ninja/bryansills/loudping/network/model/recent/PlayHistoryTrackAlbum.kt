package ninja.bryansills.loudping.network.model.recent

import kotlinx.serialization.Serializable

@Serializable
data class PlayHistoryTrackAlbum(
    val id: String,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
)
