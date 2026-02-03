package ninja.bryansills.loudping.network

import com.slack.eithernet.ApiResult
import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SeveralAlbumsResponse
import ninja.bryansills.loudping.network.model.SeveralTracksResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SpotifyService {
    @GET("/v1/me")
    suspend fun getMe(): ApiResult<PrivateUserResponse, Unit>

    @GET("/v1/me/player/recently-played")
    suspend fun getRecentlyPlayed(
        @Query("limit") limit: Int = 50,
        @Query("before") beforeTimestampUnix: Long? = null,
    ): ApiResult<RecentlyPlayedResponse, Unit>

    @GET
    suspend fun getOlderRecentlyPlayed(@Url fullUrl: String): ApiResult<RecentlyPlayedResponse, Unit>

    @GET("/v1/me/albums")
    suspend fun getSavedAlbums(
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0,
    ): ApiResult<SeveralAlbumsResponse, Unit>

    /**
     * @param ids A comma-separated list of the Spotify IDs.
     * For example: ids=4iV5W9uYEdYUVa79Axb7Rh,1301WleyT98MSxVHPZCA6M.
     * Maximum: 50 IDs.
     */
    @GET("/v1/tracks")
    suspend fun getSeveralTracks(@Query("ids") ids: String): ApiResult<SeveralTracksResponse, Unit>

    /**
     * @param ids A comma-separated list of the Spotify IDs.
     * For example: ids=4iV5W9uYEdYUVa79Axb7Rh,1301WleyT98MSxVHPZCA6M.
     * Maximum: 20 IDs.
     */
    @GET("/v1/albums")
    suspend fun getSeveralAlbums(@Query("ids") ids: String): ApiResult<SeveralAlbumsResponse, Unit>

    @GET
    suspend fun getSeveralAlbumsUrl(@Url fullUrl: String): ApiResult<SeveralAlbumsResponse, Unit>
}
