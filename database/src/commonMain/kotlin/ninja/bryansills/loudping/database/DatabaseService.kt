package ninja.bryansills.loudping.database

import kotlinx.datetime.Instant

interface DatabaseService {
    suspend fun insertAlbumPlayRecord(
        albumId: Long,
        timestamp: Instant,
    )
}
