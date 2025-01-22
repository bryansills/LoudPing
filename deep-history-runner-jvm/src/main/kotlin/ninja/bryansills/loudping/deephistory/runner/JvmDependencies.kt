package ninja.bryansills.loudping.deephistory.runner

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import kotlin.time.Duration.Companion.seconds
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.DriverFactory
import ninja.bryansills.loudping.database.RealDatabaseService
import ninja.bryansills.loudping.database.createDatabase
import ninja.bryansills.loudping.jvm.network.runner.BuildConfig
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.RealNetworkService
import ninja.bryansills.loudping.network.SpotifyService
import ninja.bryansills.loudping.network.auth.AccessTokenInterceptor
import ninja.bryansills.loudping.network.auth.AuthorizationHeaderInterceptor
import ninja.bryansills.loudping.network.auth.RealAuthManager
import ninja.bryansills.loudping.network.auth.SpotifyAuthService
import ninja.bryansills.loudping.network.rate.RateLimitInterceptor
import ninja.bryansills.loudping.network.rate.RateLimiter
import ninja.bryansills.loudping.network.rate.Sleeper
import ninja.bryansills.loudping.repository.album.AlbumRepository
import ninja.bryansills.loudping.repository.album.RealAlbumRepository
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
internal suspend fun initializeDependencies(): JvmDependencies {
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
        File("deep-history-jvm.preferences_pb")
    }
    val simpleStorage = RealSimpleStorage(dataStore)
    // preload SimpleStorage with the bundled refresh token
    simpleStorage.edit { it[Stored.RefreshToken.key] = BuildConfig.JvmRefreshToken }

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
        .addConverterFactory(converterFactory)
        .build()

    val spotifyAuthService = authRetrofit.create<SpotifyAuthService>()

    val authManager = RealAuthManager(
        simpleStorage = simpleStorage,
        spotifyAuthService = spotifyAuthService,
        timeProvider = timeProvider,
        networkSneak = networkSneak,
    )

    val accessTokenInterceptor = AccessTokenInterceptor(authManager)
    val rateLimitInterceptor = RateLimitInterceptor(
        rateLimiter = RateLimiter(
            timeProvider = timeProvider,
            sleeper = Sleeper.Default,
            permitsPerWindow = 5,
            windowSize = 1.seconds,
        ),
    )

    val mainOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(accessTokenInterceptor)
        .addInterceptor(rateLimitInterceptor)
        .build()

    val mainRetrofit = Retrofit.Builder()
        .baseUrl(networkSneak.baseApiUrl)
        .client(mainOkHttpClient)
        .addConverterFactory(converterFactory)
        .build()

    val spotifyService = mainRetrofit.create<SpotifyService>()
    val networkService = RealNetworkService(spotifyService = spotifyService)

    val driverFactory = DriverFactory()
    val sqlDatabase = createDatabase(driverFactory)
    val databaseService = RealDatabaseService(database = sqlDatabase)

    return JvmDependencies(
        network = networkService,
        database = databaseService,
        albumRepo = RealAlbumRepository(
            network = networkService,
            database = databaseService,
        ),
    )
}

data class JvmDependencies(
    val network: NetworkService,
    val database: DatabaseService,
    val albumRepo: AlbumRepository,
)
