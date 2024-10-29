package ninja.bryansills.loudping.database

import app.cash.sqldelight.async.coroutines.awaitAsList
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import ninja.bryansills.loudping.database.model.TrackPlayContext
import ninja.bryansills.loudping.database.model.TrackPlayRecord

class RealDatabaseService(
    private val database: Database,
    private val timestampFormatter: DateTimeFormat<DateTimeComponents> = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET,
) : DatabaseService {
    override suspend fun insertTrackPlayRecords(records: List<TrackPlayRecord>) {
        val queries = database.trackPlayRecordQueries
        database.transaction {
            records.forEach { record ->
                queries.insert(
                    record.trackId,
                    record.trackNumber.toLong(),
                    record.trackTitle,
                    record.albumId,
                    record.timestamp.format(timestampFormatter),
                    record.context,
                )
            }
        }
    }

    override suspend fun insertAlbumPlayRecord(albumId: Long, timestamp: Instant) {
        val queries = database.albumPlayRecordQueries
        queries.insert(albumId, timestamp.toString())
    }

    override suspend fun insertTrackPlayGap(start: Instant, end: Instant) {
        val queries = database.trackPlayGapQueries
        queries.insert(
            start = start.format(timestampFormatter),
            end = end.format(timestampFormatter),
        )
    }

    override suspend fun getAllPlayedTracks(): List<TrackPlayRecord> {
        return database
            .trackPlayRecordQueries
            .select_all()
            .awaitAsList()
            .map { db ->
                TrackPlayRecord(
                    trackId = db.track_id,
                    trackNumber = db.track_number.toInt(),
                    trackTitle = db.track_title,
                    albumId = db.album_id,
                    timestamp = Instant.parse(db.timestamp),
                    context = db.context ?: TrackPlayContext.Unknown,
                )
            }
    }
}
