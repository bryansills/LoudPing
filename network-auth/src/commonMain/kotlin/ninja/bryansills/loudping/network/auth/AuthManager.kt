package ninja.bryansills.loudping.network.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface AuthManager {
    /**
     * @param startTime An Instant in which this request starts. This value should be kept around
     * by the client. It will be provided to `setAuthorizationCode()` after the service returns back
     * an authorization code.
     */
    fun getAuthorizeUrl(startTime: Instant): String

    /**
     * The website gives us an authorization code and we give back a refresh token
     *
     * @param state Provided by the service
     * @param code Provided by the service
     * @param startTime The same value that as used in `getAuthorizeUrl()`
     *
     * @return The access token for the now logged-in user
     */
    suspend fun setAuthorizationCode(state: String, code: String, startTime: Instant): String

    /**
     * The normal login flow is busted, so let's try and get around that...
     *
     * @param refreshToken I don't wanna know where you got this from
     *
     * @return The access token for the now logged-in user
     */
    suspend fun setRefreshToken(refreshToken: String): String

    suspend fun getValidAccessToken(): String

    val rawValues: Flow<RawAuthValues>
}

data class RawAuthValues(
    val accessToken: String,
    val accessTokenExpiresAt: Instant,
    val refreshToken: String,
)
