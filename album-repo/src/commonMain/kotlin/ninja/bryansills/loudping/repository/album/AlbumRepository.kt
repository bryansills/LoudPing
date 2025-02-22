package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.core.model.Album

interface AlbumRepository {
    suspend fun getAlbumByTrackId(trackId: String, shouldQueryNetwork: Boolean = false): Album?

    /**
     * TODO: migrate this to support partial success
     */
    suspend fun getAlbumsByTrackIds(trackIds: List<String>): List<Album>
}
