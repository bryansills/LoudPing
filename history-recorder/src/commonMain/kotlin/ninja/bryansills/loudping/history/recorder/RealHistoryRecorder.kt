package ninja.bryansills.loudping.history.recorder

import com.slack.eithernet.successOrNothing
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.core.model.Artist
import ninja.bryansills.loudping.core.model.Track
import ninja.bryansills.loudping.core.model.TrackAlbum
import ninja.bryansills.loudping.core.model.TrackPlayContext
import ninja.bryansills.loudping.core.model.TrackPlayRecord
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.network.GetRecentlyPlayed
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist
import ninja.bryansills.loudping.network.model.recent.ContextType
import ninja.bryansills.loudping.network.model.recent.PlayHistoryItem
import ninja.bryansills.loudping.network.model.recent.RecentTrimmingStrategy
import ninja.bryansills.loudping.network.model.track.coverImageUrl

class RealHistoryRecorder(
    private val getRecentlyPlayed: GetRecentlyPlayed,
    private val databaseService: DatabaseService,
) : HistoryRecorder {
    override suspend operator fun invoke(
        startAt: Instant,
        stopAt: Instant?,
    ): Result<TrackHistoryResult> {
        if (stopAt != null) {
            check(startAt > stopAt)
        }

        val networkResponse = getRecentlyPlayed(
            startAt = startAt,
            stopAt = stopAt ?: Instant.DISTANT_PAST,
            trimmingStrategy = RecentTrimmingStrategy.None,
        )
            .map { it.successOrNothing { throw RuntimeException() } }
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
                track = Track(
                    spotifyId = networkTrack.track.id,
                    title = networkTrack.track.name,
                    trackNumber = networkTrack.track.track_number,
                    discNumber = networkTrack.track.disc_number,
                    duration = networkTrack.track.duration_ms.milliseconds,
                    album = TrackAlbum(
                        spotifyId = networkTrack.track.album.id,
                        title = networkTrack.track.album.name,
                        trackCount = networkTrack.track.album.total_tracks,
                        coverImage = networkTrack.track.album.coverImageUrl,
                    ),
                    artists = networkTrack.track.artists.toDatabase(),
                ),
                timestamp = networkTrack.played_at,
                context = networkTrack.context.type.toDatabase(),
            )
        }
}

private fun List<SimplifiedArtist>.toDatabase(): List<Artist> {
    return this.map { networkArtist ->
        Artist(
            spotifyId = networkArtist.id,
            name = networkArtist.name,
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
