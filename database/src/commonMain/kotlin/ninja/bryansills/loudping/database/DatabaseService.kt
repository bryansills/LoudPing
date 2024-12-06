package ninja.bryansills.loudping.database

import androidx.paging.PagingSource
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

    suspend fun getAllPlayedTracks(): List<TrackPlayRecord>

    val playedTracks: PagingSource<String, TrackPlayRecord>
}
