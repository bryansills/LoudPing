package ninja.bryansills.loudping.network

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SavedAlbumsResponse
import ninja.bryansills.loudping.network.model.recent.RecentTrimmingStrategy

interface NetworkService {
    suspend fun getMe(): PrivateUserResponse
    suspend fun getRecentlyPlayed(): RecentlyPlayedResponse
    suspend fun getSavedAlbums(): SavedAlbumsResponse

    fun getRecentlyPlayedStream(
        startAt: Instant,
        stopAt: Instant = Instant.DISTANT_PAST,
        trimmingStrategy: RecentTrimmingStrategy = RecentTrimmingStrategy.StopAt,
    ): Flow<RecentlyPlayedResponse>
}
