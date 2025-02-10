package ninja.bryansills.loudping.network

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SavedAlbumsResponse
import ninja.bryansills.loudping.network.model.recent.PlayHistoryItem
import ninja.bryansills.loudping.network.model.recent.RecentTrimmingStrategy
import ninja.bryansills.loudping.network.model.track.Track

class RealNetworkService(
    private val spotifyService: SpotifyService,
) : NetworkService {
    override suspend fun getMe(): PrivateUserResponse {
        return spotifyService.getMe()
    }

    override suspend fun getRecentlyPlayed(): RecentlyPlayedResponse {
        return spotifyService.getRecentlyPlayed()
    }

    override suspend fun getSavedAlbums(): SavedAlbumsResponse {
        return spotifyService.getSavedAlbums()
    }

    override fun getRecentlyPlayedStream(
        startAt: Instant,
        stopAt: Instant,
        trimmingStrategy: RecentTrimmingStrategy,
    ): Flow<RecentlyPlayedResponse> = flow {
        var currentQueryTime: Instant? = startAt
        var nextUrl: String? = null
        var isFirstQuery = true

        while (
            currentCoroutineContext().isActive && keepGoing(
                currentQueryTime,
                stopAt,
                nextUrl,
                isFirstQuery,
            )
        ) {
            val response = if (nextUrl != null) {
                spotifyService.getOlderRecentlyPlayed(nextUrl)
            } else {
                val beforeInMillis = currentQueryTime!!.toEpochMilliseconds()
                spotifyService.getRecentlyPlayed(beforeTimestampUnix = beforeInMillis)
            }

            val trimmedResponse = when (trimmingStrategy) {
                RecentTrimmingStrategy.None -> response
                RecentTrimmingStrategy.StopAt -> {
                    response.copy(items = response.items.trim(stopAt))
                }
            }
            emit(trimmedResponse)

            currentQueryTime = try {
                Instant.fromEpochMilliseconds(response.cursors!!.before.toLong())
            } catch (ex: Exception) {
                null
            }
            nextUrl = response.next
            isFirstQuery = false
        }
    }

    override suspend fun getSeveralTracks(ids: List<String>): List<Track> {
        require(ids.size <= 50) { "You can only query for 50 tracks at a time." }
        return spotifyService.getSeveralTracks(ids.joinToString(separator = ",")).tracks
    }
}

private fun keepGoing(
    inProgress: Instant?,
    stopAt: Instant,
    nextUrl: String?,
    isFirstQuery: Boolean,
): Boolean {
    return if (isFirstQuery) {
        inProgress != null && inProgress > stopAt
    } else {
        nextUrl != null && inProgress != null && inProgress > stopAt
    }
}

private fun List<PlayHistoryItem>.trim(olderThan: Instant): List<PlayHistoryItem> {
    return this.filter { it.played_at > olderThan }
}
