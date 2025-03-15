package ninja.bryansills.loudping.network.model

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.album.FullAlbum

@Serializable
data class SeveralAlbumsResponse(val albums: List<FullAlbum>)
