package ninja.bryansills.loudping.history.recorder

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.model.TrackPlayRecord
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.recent.ContextType

class RealHistoryRecorder(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
) : HistoryRecorder {
    override suspend operator fun invoke(
        startAt: Instant,
        stopAt: Instant?,
    ): Result<TrackHistoryResult> = coroutineScope {
        if (stopAt != null) {
            check(startAt > stopAt)
        }

        val networkResponse = networkService
            .getRecentlyPlayedStream(startAt, stopAt ?: Instant.DISTANT_PAST)
            .toList()
        val databaseEntries = networkResponse.toDatabase()

        databaseService.insertTrackPlayRecords(databaseEntries)

        val result = if (databaseEntries.isNotEmpty()) {
            TrackHistoryResult.Standard(
                oldestTimestamp = databaseEntries.first().timestamp,
                newestTimestamp = databaseEntries.last().timestamp,
            )
        } else {
            TrackHistoryResult.NothingPlayed
        }
        Result.success(result)
    }
}

private fun List<RecentlyPlayedResponse>.toDatabase(): List<TrackPlayRecord> {
    return this
        .flatMap { networkRecord -> networkRecord.items }
        .filter { networkTrack ->
            networkTrack.context.type == ContextType.Album
        }
        .sortedBy { it.played_at }
        .map { networkTrack ->
            TrackPlayRecord(
                trackId = networkTrack.track.id,
                trackNumber = networkTrack.track.track_number,
                trackTitle = networkTrack.track.name,
                albumId = networkTrack.track.album.id,
                timestamp = networkTrack.played_at,
            )
        }
}
