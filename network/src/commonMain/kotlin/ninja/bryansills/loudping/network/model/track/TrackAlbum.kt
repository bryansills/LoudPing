package ninja.bryansills.loudping.network.model.track

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.album.AlbumType
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist
import ninja.bryansills.loudping.network.model.common.ReleaseDatePrecision

@Serializable
data class TrackAlbum(
    val id: String,
    val name: String,
    val album_type: AlbumType,
    val total_tracks: Int,
    val release_date: String,
    val release_date_precision: ReleaseDatePrecision,
    val images: List<AlbumImage>,
    val artists: List<SimplifiedArtist>,
) {
    @Serializable
    data class AlbumImage(
        val url: String,
        val height: Int? = -1,
        val width: Int? = -1,
    )
}

val TrackAlbum.coverImageUrl: String?
    get() = this.images.maxByOrNull { (it.height ?: 0) * (it.width ?: 0) }?.url
