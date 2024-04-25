package ninja.bryansills.loudping.network

import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SavedAlbumsResponse

interface NetworkService {
    suspend fun getMe(): PrivateUserResponse
    suspend fun getRecentlyPlayed(): RecentlyPlayedResponse
    suspend fun getSavedAlbums(): SavedAlbumsResponse
}
