package ninja.bryansills.loudping.network.auth

import com.slack.eithernet.ApiResult
import com.slack.eithernet.exceptionOrNull
import com.slack.eithernet.successOrNothing
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.session.Stored
import ninja.bryansills.loudping.sneak.network.NetworkSneak
import ninja.bryansills.loudping.storage.SimpleStorage
import ninja.bryansills.loudping.storage.first
import ninja.bryansills.loudping.time.TimeProvider
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

class RealAuthManager(
    private val simpleStorage: SimpleStorage,
    private val spotifyAuthService: SpotifyAuthService,
    private val timeProvider: TimeProvider,
    private val networkSneak: NetworkSneak,
) : AuthManager {
    private val authorizeUrlSalt = networkSneak.redirectUrl.toRandomString()

    private fun getFullState(timestamp: Instant): String {
        return "$timestamp$authorizeUrlSalt".toRandomString()
    }

    override fun getAuthorizeUrl(startTime: Instant): String {
        val result = networkSneak
            .authorizeUrl
            .httpUrlBuilder {
                addQueryParameter("response_type", "code")
                addQueryParameter("client_id", networkSneak.clientId)
                addQueryParameter("scope", "user-read-recently-played user-library-read user-read-private")
                addQueryParameter("redirect_uri", networkSneak.redirectUrl)
                addQueryParameter("state", getFullState(startTime))
            }
            .toString()

        return result
    }

    override suspend fun setAuthorizationCode(state: String, code: String, startTime: Instant): String {
        val localCalculatedState = getFullState(startTime)
        require(state == localCalculatedState)

        resetAuthenticationState()

        val apiResponse = spotifyAuthService.requestTokens(
            grantType = "authorization_code",
            code = code,
            redirectUri = networkSneak.redirectUrl,
        )

        val successResponse = apiResponse.successOrNothing { failure ->
            throw failure.exceptionOrNull() ?: RuntimeException(failure.toString())
        }

        return setRefreshToken(successResponse.refresh_token)
    }

    override suspend fun setRefreshToken(refreshToken: String): String {
        return getValidAccessTokenInternal { refreshToken }
    }

    override suspend fun getValidAccessToken(): String {
        return getValidAccessTokenInternal {
            simpleStorage.first(Stored.RefreshToken)
        }
    }

    private suspend fun getValidAccessTokenInternal(
        provideRefreshToken: suspend () -> String,
    ): String {
        val currentAccessToken = simpleStorage.first(Stored.AccessToken)
        val currentAccessTokenExpiresAt = Instant.parse(
            simpleStorage.first(Stored.AccessTokenExpiresAt),
        )

        if (timeProvider.now < currentAccessTokenExpiresAt) {
            return currentAccessToken
        } else {
            simpleStorage.edit {
                it.remove(Stored.AccessToken.key)
                it.remove(Stored.AccessTokenExpiresAt.key)
            }
        }

        val refreshToken = provideRefreshToken()
        val apiResponse = spotifyAuthService.refreshTokens(
            grantType = "refresh_token",
            refreshToken = refreshToken,
            clientId = networkSneak.clientId,
        )

        val successResponse = apiResponse.successOrNothing { failure ->
            (failure as? ApiResult.Failure.HttpFailure)?.let { httpFailure ->
                when (httpFailure.code) {
                    401 -> {
                        simpleStorage.edit {
                            it.remove(Stored.AccessToken.key)
                            it.remove(Stored.AccessTokenExpiresAt.key)
                        }
                    }
                    429 -> {
                        resetAuthenticationState()
                    }
                    else -> {}
                }
            }
            throw failure.exceptionOrNull() ?: RuntimeException(failure.toString())
        }

        simpleStorage.edit {
            it[Stored.RefreshToken.key] = refreshToken
            it[Stored.AccessToken.key] = successResponse.access_token
            val expiresAt = timeProvider.now + successResponse.expires_in.seconds
            it[Stored.AccessTokenExpiresAt.key] = expiresAt.toString()
        }

        return successResponse.access_token
    }

    override val rawValues: Flow<RawAuthValues> = combine(
        simpleStorage[Stored.AccessToken],
        simpleStorage[Stored.AccessTokenExpiresAt],
        simpleStorage[Stored.RefreshToken],
    ) { accessToken, accessTokenExpiresAt, refreshToken ->
        RawAuthValues(accessToken, Instant.parse(accessTokenExpiresAt), refreshToken)
    }

    private suspend fun resetAuthenticationState() {
        simpleStorage.edit {
            it.remove(Stored.RefreshToken.key)
            it.remove(Stored.AccessToken.key)
            it.remove(Stored.AccessTokenExpiresAt.key)
        }
    }
}

private inline fun String.httpUrlBuilder(block: HttpUrl.Builder.() -> Unit): HttpUrl {
    return this.toHttpUrl().newBuilder().apply { block() }.build()
}

private fun String.toRandomString(length: Int = 16): String {
    val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val randomInstance = Random(this.hashCode())
    return (1..length)
        .map { alphabet.random(randomInstance) }
        .joinToString("")
}
