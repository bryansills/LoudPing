package ninja.bryansills.loudping.network.auth

import kotlinx.datetime.Instant

interface AuthManager {
    /**
     * @param startTime An Instant in which this request starts. This value should be kept around
     * by the client. It will be provided to `setAuthorizationCode()` after the service returns back
     * an authorization code.
     */
    fun getAuthorizeUrl(startTime: Instant): String

    suspend fun getAccessToken(): String

    /**
     * The website gives us an authorization code and we give back a refresh token
     *
     * @param state Provided by the service
     * @param code Provided by the service
     * @param startTime The same value that as used in `getAuthorizeUrl()`
     */
    suspend fun setAuthorizationCode(state: String, code: String, startTime: Instant): String
}
