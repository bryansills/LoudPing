package ninja.bryansills.loudping.jvm.network.runner

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import java.io.File
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.network.SpotifyService
import ninja.bryansills.loudping.network.auth.AccessTokenInterceptor
import ninja.bryansills.loudping.network.auth.AuthorizationHeaderInterceptor
import ninja.bryansills.loudping.network.auth.RealAuthManager
import ninja.bryansills.loudping.network.auth.SpotifyAuthService
import ninja.bryansills.loudping.session.Stored
import ninja.bryansills.loudping.sneak.network.RealNetworkSneak
import ninja.bryansills.loudping.storage.RealSimpleStorage
import ninja.bryansills.loudping.time.RealTimeProvider
import ninja.bryansills.sneak.Sneak
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@OptIn(ExperimentalStdlibApi::class)
internal fun initializeDependencies(): SpotifyService {
    val sneak = Sneak(BuildConfig.SneakSalt.toByteArray())
    val networkSneak = RealNetworkSneak(
        sneak = sneak,
        obfuscatedClientId = BuildConfig.SneakClientId.hexToByteArray(),
        obfuscatedClientSecret = BuildConfig.SneakClientSecret.hexToByteArray(),
        obfuscatedRedirectUrl = BuildConfig.SneakRedirectUrl.hexToByteArray(),
        obfuscatedBaseApiUrl = BuildConfig.SneakBaseApiUrl.hexToByteArray(),
        obfuscatedBaseAuthApiUrl = BuildConfig.SneakBaseAuthApiUrl.hexToByteArray(),
        obfuscatedAuthorizeUrl = BuildConfig.SneakAuthorizeUrl.hexToByteArray(),
    )

    val dataStore = PreferenceDataStoreFactory.create {
        File("fake-jvm-storage.preferences_pb")
    }
    val simpleStorage = RealSimpleStorage(dataStore)

    // preload SimpleStorage with the bundled refresh token
    runBlocking {
        simpleStorage.edit {
            it[Stored.RefreshToken.key] = BuildConfig.JvmRefreshToken
        }
    }

    val timeProvider = RealTimeProvider()

    val json = Json {
        ignoreUnknownKeys = true
    }
    val converterFactory = json.asConverterFactory(
        "application/json; charset=UTF8".toMediaType(),
    )

    val authorizationHeaderInterceptor = AuthorizationHeaderInterceptor(networkSneak)

    val authOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authorizationHeaderInterceptor)
        .build()

    val authRetrofit = Retrofit.Builder()
        .baseUrl(networkSneak.baseAuthApiUrl)
        .client(authOkHttpClient)
        .addConverterFactory(ApiResultConverterFactory)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(ApiResultCallAdapterFactory)
        .build()

    val spotifyAuthService = authRetrofit.create<SpotifyAuthService>()

    val authManager = RealAuthManager(
        simpleStorage = simpleStorage,
        spotifyAuthService = spotifyAuthService,
        timeProvider = timeProvider,
        networkSneak = networkSneak,
    )

    val accessTokenInterceptor = AccessTokenInterceptor(authManager)

    val mainOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(accessTokenInterceptor)
        .build()

    val mainRetrofit = Retrofit.Builder()
        .baseUrl(networkSneak.baseApiUrl)
        .client(mainOkHttpClient)
        .addConverterFactory(converterFactory)
        .build()

    return mainRetrofit.create<SpotifyService>()
}
