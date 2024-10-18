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
    ): Result<Instant> = coroutineScope {
        if (stopAt != null) {
            check(startAt > stopAt)
        }

        val networkResponse = networkService
            .getRecentlyPlayedStream(startAt, stopAt ?: Instant.DISTANT_PAST)
            .toList()

        databaseService.insertTrackPlayRecords(networkResponse.toDatabase())

        Result.success(networkResponse.first().items.first().played_at)
    }
}

private fun List<RecentlyPlayedResponse>.toDatabase(): List<TrackPlayRecord> {
    return this
        .flatMap { networkRecord -> networkRecord.items }
        .filter { networkTrack ->
            networkTrack.context.type == ContextType.Album
        }
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
