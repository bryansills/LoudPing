package ninja.bryansills.loudping.network

import com.slack.eithernet.ApiResult
import com.slack.eithernet.successOrNothing
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.recent.PlayHistoryItem
import ninja.bryansills.loudping.network.model.recent.RecentTrimmingStrategy

interface GetRecentlyPlayed {
    operator fun invoke(
        startAt: Instant,
        stopAt: Instant = Instant.DISTANT_PAST,
        trimmingStrategy: RecentTrimmingStrategy = RecentTrimmingStrategy.StopAt,
    ): Flow<ApiResult<RecentlyPlayedResponse, Unit>>
}

class RealGetRecentlyPlayed(
    private val spotifyService: SpotifyService,
) : GetRecentlyPlayed {
    override fun invoke(
        startAt: Instant,
        stopAt: Instant,
        trimmingStrategy: RecentTrimmingStrategy,
    ): Flow<ApiResult<RecentlyPlayedResponse, Unit>> = flow {
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

            val successfulResponse = response.successOrNothing { throw RuntimeException() }

            val trimmedResponse = when (trimmingStrategy) {
                RecentTrimmingStrategy.None -> successfulResponse

                RecentTrimmingStrategy.StopAt -> {
                    successfulResponse.copy(items = successfulResponse.items.trim(stopAt))
                }
            }
            emit(ApiResult.success(trimmedResponse))

            currentQueryTime = try {
                Instant.fromEpochMilliseconds(successfulResponse.cursors!!.before.toLong())
            } catch (ex: Exception) {
                null
            }
            nextUrl = successfulResponse.next
            isFirstQuery = false
        }
    }
}

private fun keepGoing(
    inProgress: Instant?,
    stopAt: Instant,
    nextUrl: String?,
    isFirstQuery: Boolean,
): Boolean = if (isFirstQuery) {
    inProgress != null && inProgress > stopAt
} else {
    nextUrl != null && inProgress != null && inProgress > stopAt
}

private fun List<PlayHistoryItem>.trim(olderThan: Instant): List<PlayHistoryItem> = this.filter { it.played_at > olderThan }
