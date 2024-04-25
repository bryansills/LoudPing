package ninja.bryansills.loudping.network.model

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.album.SavedAlbumItem

@Serializable
data class SavedAlbumsResponse(
    val next: String?,
    val items: List<SavedAlbumItem>,
)
