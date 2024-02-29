package ninja.bryansills.loudping.network

import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse

interface NetworkService {
    suspend fun getMe(): PrivateUserResponse
    suspend fun getRecentlyPlayed(): RecentlyPlayedResponse
}
