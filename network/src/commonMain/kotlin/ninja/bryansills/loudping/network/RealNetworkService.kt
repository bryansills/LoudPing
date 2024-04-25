package ninja.bryansills.loudping.network

import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SavedAlbumsResponse

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
}
