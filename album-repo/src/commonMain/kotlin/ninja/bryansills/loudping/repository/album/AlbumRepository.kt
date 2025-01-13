package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.database.model.Album

interface AlbumRepository {
    suspend fun getAlbumById(id: String, shouldQueryNetwork: Boolean = false): Album?
}
