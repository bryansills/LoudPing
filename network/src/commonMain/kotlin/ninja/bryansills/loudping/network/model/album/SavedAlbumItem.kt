package ninja.bryansills.loudping.network.model.album

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable data class SavedAlbumItem(val added_at: Instant, val album: SavedAlbumAlbum)
