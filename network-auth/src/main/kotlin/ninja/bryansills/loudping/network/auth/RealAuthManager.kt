package ninja.bryansills.loudping.network.auth

import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.app.sneak.BuildSneak
import ninja.bryansills.loudping.network.auth.model.TokenResponse
import ninja.bryansills.loudping.session.Stored
import ninja.bryansills.loudping.storage.SimpleStorage
import ninja.bryansills.loudping.storage.first
import ninja.bryansills.loudping.time.TimeProvider
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

class RealAuthManager @Inject constructor(
    private val simpleStorage: SimpleStorage,
    private val spotifyAuthService: SpotifyAuthService,
    private val timeProvider: TimeProvider,
    private val buildSneak: BuildSneak,
) : AuthManager {
    private val authorizeUrlSalt = buildSneak.redirectUrl.toRandomString()

    private fun getFullState(timestamp: Instant): String {
        return "$timestamp$authorizeUrlSalt".toRandomString()
    }

    override fun getAuthorizeUrl(startTime: Instant): String {
        return buildSneak
            .authorizeUrl
            .httpUrlBuilder {
                addQueryParameter("response_type", "code")
                addQueryParameter("client_id", buildSneak.clientId)
                addQueryParameter("scope", "user-read-recently-played user-library-read user-read-private")
                addQueryParameter("redirect_uri", buildSneak.redirectUrl)
                addQueryParameter("state", getFullState(startTime))
            }
            .toString()
    }

    override suspend fun getAccessToken(): String {
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

        val response = spotifyAuthService.refreshTokens(
            grantType = "refresh_token",
            refreshToken = simpleStorage.first(Stored.RefreshToken),
            clientId = buildSneak.clientId,
        )

        simpleStorage.update(response)

        return response.access_token
    }

    override suspend fun setAuthorizationCode(state: String, code: String, startTime: Instant): String {
        require(state == getFullState(startTime))

        simpleStorage.edit {
            it.remove(Stored.RefreshToken.key)
            it.remove(Stored.AccessToken.key)
            it.remove(Stored.AccessTokenExpiresAt.key)
        }

        val response = spotifyAuthService.requestTokens(
            grantType = "authorization_code",
            code = code,
            redirectUri = buildSneak.redirectUrl,
        )

        simpleStorage.update(response)

        return response.refresh_token
    }

    private suspend fun SimpleStorage.update(response: TokenResponse) {
        this.edit {
            it[Stored.RefreshToken.key] = response.refresh_token
            it[Stored.AccessToken.key] = response.access_token

            val expiresAt = timeProvider.now + response.expires_in.seconds
            it[Stored.AccessTokenExpiresAt.key] = expiresAt.toString()
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
