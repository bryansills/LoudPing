package ninja.bryansills.loudping.network.model.recent

import kotlinx.serialization.Serializable

@Serializable
data class PlayHistoryTrack(
    val id: String,
    val name: String,
    val track_number: Int,
    val album: PlayHistoryTrackAlbum,
)
