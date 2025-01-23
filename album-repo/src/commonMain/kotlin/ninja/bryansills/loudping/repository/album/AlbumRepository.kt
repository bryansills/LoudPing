package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.database.model.Album

interface AlbumRepository {
    suspend fun getAlbumByTrackId(trackId: String, shouldQueryNetwork: Boolean = false): Album?

    /**
     * ALWAYS makes a network request, so try the other method first.
     */
    suspend fun getAlbumsByTrackIds(trackIds: List<String>): List<Album>
}
