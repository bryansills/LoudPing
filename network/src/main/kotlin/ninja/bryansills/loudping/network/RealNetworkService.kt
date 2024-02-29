package ninja.bryansills.loudping.network

import javax.inject.Inject
import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse

class RealNetworkService @Inject constructor(
    private val spotifyService: SpotifyService,
) : NetworkService {
    override suspend fun getMe(): PrivateUserResponse {
        return spotifyService.getMe()
    }

    override suspend fun getRecentlyPlayed(): RecentlyPlayedResponse {
        return spotifyService.getRecentlyPlayed()
    }
}
