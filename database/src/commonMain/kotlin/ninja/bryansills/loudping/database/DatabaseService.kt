package ninja.bryansills.loudping.database

import kotlinx.datetime.Instant
import ninja.bryansills.loudping.database.model.TrackPlayRecord

interface DatabaseService {
    suspend fun insertTrackPlayRecords(records: List<TrackPlayRecord>)

    suspend fun insertAlbumPlayRecord(
        albumId: Long,
        timestamp: Instant,
    )

    suspend fun insertTrackPlayGap(
        start: Instant,
        end: Instant,
    )
}
