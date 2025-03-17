package ninja.bryansills.loudping.network.model.album

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist
import ninja.bryansills.loudping.network.model.common.ReleaseDatePrecision

@Serializable
data class FullAlbum(
    val album_type: AlbumType,
    val total_tracks: Int,
    val id: String,
    val name: String,
    val images: List<AlbumImage>,
    val release_date: String,
    val release_date_precision: ReleaseDatePrecision,
    val artists: List<SimplifiedArtist>,
    val tracks: AlbumTracks,
)

@Serializable
data class AlbumTracks(
    val next: String,
    val total: Int,
    val items: List<AlbumTrack>,
)

@Serializable
data class AlbumTrack(
    val id: String,
    val name: String,
    val disc_number: Int,
    val duration_ms: Int,
    val track_number: Int,
    val artists: List<SimplifiedArtist>,
)

@Serializable
data class AlbumImage(
    val url: String,
    val width: Int?,
    val height: Int?,
)
