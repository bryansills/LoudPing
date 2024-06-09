package ninja.bryansills.loudping.database

import kotlinx.datetime.Instant

class RealDatabaseService(private val database: Database) : DatabaseService {
    override suspend fun insertAlbumPlayRecord(albumId: Long, timestamp: Instant) {
        val queries = database.albumPlayRecordQueries
        queries.insert(albumId, timestamp.toString())
    }
}
