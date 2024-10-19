package ninja.bryansills.loudping.database

import kotlinx.datetime.Instant
import ninja.bryansills.loudping.database.model.TrackPlayRecord

class RealDatabaseService(private val database: Database) : DatabaseService {
    override suspend fun insertTrackPlayRecords(records: List<TrackPlayRecord>) {
        val queries = database.trackPlayRecordQueries
        database.transaction {
            records.forEach { record ->
                queries.insert(
                    record.trackId,
                    record.trackNumber.toLong(),
                    record.trackTitle,
                    record.albumId,
                    record.timestamp.toString(),
                    record.context,
                )
            }
        }
    }

    override suspend fun insertAlbumPlayRecord(albumId: Long, timestamp: Instant) {
        val queries = database.albumPlayRecordQueries
        queries.insert(albumId, timestamp.toString())
    }
}
