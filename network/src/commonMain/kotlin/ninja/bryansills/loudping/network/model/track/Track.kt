package ninja.bryansills.loudping.network.model.track

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist

@Serializable
data class Track(
    val id: String,
    val name: String,
    val disc_number: Int,
    val duration_ms: Int,
    val track_number: Int,
    val album: TrackAlbum,
    val artists: List<SimplifiedArtist>,
)
