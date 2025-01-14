package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.database.model.Album

interface AlbumRepository {
    suspend fun getAlbumByTrackId(trackId: String, shouldQueryNetwork: Boolean = false): Album?

    suspend fun getAlbumsByTrackIds(trackIds: List<String>): Map<String, Album>
}
