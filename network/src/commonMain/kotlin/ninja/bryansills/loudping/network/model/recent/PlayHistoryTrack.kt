package ninja.bryansills.loudping.network.model.recent

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist

@Serializable
data class PlayHistoryTrack(
    val id: String,
    val name: String,
    val track_number: Int,
    val album: PlayHistoryTrackAlbum,
    val artists: List<SimplifiedArtist>,
)
