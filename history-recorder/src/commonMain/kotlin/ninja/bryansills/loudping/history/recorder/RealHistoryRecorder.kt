package ninja.bryansills.loudping.history.recorder

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.model.TrackPlayContext
import ninja.bryansills.loudping.database.model.TrackPlayRecord
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.recent.ContextType
import ninja.bryansills.loudping.network.model.recent.PlayHistoryItem
import ninja.bryansills.loudping.network.model.recent.RecentTrimmingStrategy

class RealHistoryRecorder(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
) : HistoryRecorder {
    override suspend operator fun invoke(
        startAt: Instant,
        stopAt: Instant?,
    ): Result<TrackHistoryResult> {
        if (stopAt != null) {
            check(startAt > stopAt)
        }

        val networkResponse = networkService
            .getRecentlyPlayedStream(
                startAt = startAt,
                stopAt = stopAt ?: Instant.DISTANT_PAST,
                trimmingStrategy = RecentTrimmingStrategy.None,
            )
            .toList()

        val playRecords = networkResponse.toRecords()
        val hasPlaybackGap = stopAt != null && playRecords.isNotEmpty() && playRecords.none { it.played_at < stopAt }
        if (hasPlaybackGap) {
            val oldestPlayedAt = playRecords.minByOrNull { it.played_at }
            databaseService.insertTrackPlayGap(
                start = stopAt!! + 5.seconds,
                end = oldestPlayedAt!!.played_at,
            )
        }

        val databaseEntries = playRecords.toDatabase(stopAt)
        val result = if (databaseEntries.isNotEmpty()) {
            databaseService.insertTrackPlayRecords(databaseEntries)

            TrackHistoryResult.Standard(
                oldestTimestamp = databaseEntries.first().timestamp,
                newestTimestamp = databaseEntries.last().timestamp,
            )
        } else {
            TrackHistoryResult.NothingPlayed
        }

        return Result.success(result)
    }
}

private fun List<RecentlyPlayedResponse>.toRecords(): List<PlayHistoryItem> {
    return this
        .flatMap { networkRecord -> networkRecord.items }
        .sortedBy { it.played_at }
}

private fun List<PlayHistoryItem>.toDatabase(stopAt: Instant?): List<TrackPlayRecord> {
    return this
        .filter { it.played_at > (stopAt ?: Instant.DISTANT_PAST) }
        .map { networkTrack ->
            TrackPlayRecord(
                trackId = networkTrack.track.id,
                trackNumber = networkTrack.track.track_number,
                trackTitle = networkTrack.track.name,
                albumId = networkTrack.track.album.id,
                timestamp = networkTrack.played_at,
                context = networkTrack.context.type.toDatabase(),
            )
        }
}

private fun ContextType.toDatabase(): TrackPlayContext {
    return when (this) {
        ContextType.Album -> TrackPlayContext.Album
        ContextType.Artist -> TrackPlayContext.Artist
        ContextType.Playlist -> TrackPlayContext.Playlist
        is ContextType.Unknown -> TrackPlayContext.Unknown
    }
}
