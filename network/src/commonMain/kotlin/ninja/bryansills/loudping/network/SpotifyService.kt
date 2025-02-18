package ninja.bryansills.loudping.network

import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SavedAlbumsResponse
import ninja.bryansills.loudping.network.model.SeveralTracksResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SpotifyService {
    @GET("/v1/me") suspend fun getMe(): PrivateUserResponse

    @GET("/v1/me/player/recently-played")
    suspend fun getRecentlyPlayed(
        @Query("limit") limit: Int = 50,
        @Query("before") beforeTimestampUnix: Long? = null,
    ): RecentlyPlayedResponse

    @GET suspend fun getOlderRecentlyPlayed(@Url fullUrl: String): RecentlyPlayedResponse

    @GET("/v1/me/albums")
    suspend fun getSavedAlbums(
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0,
    ): SavedAlbumsResponse

    /**
     * @param ids A comma-separated list of the Spotify IDs. For example:
     *   ids=4iV5W9uYEdYUVa79Axb7Rh,1301WleyT98MSxVHPZCA6M. Maximum: 50 IDs.
     */
    @GET("/v1/tracks")
    suspend fun getSeveralTracks(@Query("ids") ids: String): SeveralTracksResponse
}
