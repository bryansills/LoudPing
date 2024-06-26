package ninja.bryansills.loudping.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.RealNetworkService
import ninja.bryansills.loudping.network.SpotifyService
import ninja.bryansills.loudping.network.auth.AccessTokenInterceptor
import ninja.bryansills.loudping.network.auth.AuthManager
import ninja.bryansills.loudping.network.auth.AuthorizationHeaderInterceptor
import ninja.bryansills.loudping.network.auth.RealAuthManager
import ninja.bryansills.loudping.network.auth.SpotifyAuthService
import ninja.bryansills.loudping.sneak.network.NetworkSneak
import ninja.bryansills.loudping.storage.SimpleStorage
import ninja.bryansills.loudping.time.TimeProvider
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@InstallIn(SingletonComponent::class)
@Module
interface NetworkModule {
    companion object {
        @Singleton
        @Provides
        fun provideAuthorizationHeaderInterceptor(
            networkSneak: NetworkSneak,
        ): AuthorizationHeaderInterceptor {
            return AuthorizationHeaderInterceptor(networkSneak)
        }

        @Singleton
        @Provides
        fun provideSpotifyAuthService(
            authorizationHeaderInterceptor: AuthorizationHeaderInterceptor,
            networkSneak: NetworkSneak,
        ): SpotifyAuthService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authorizationHeaderInterceptor)
                .build()
            val json = Json {
                ignoreUnknownKeys = true
            }
            val converterFactory = json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType(),
            )

            val retrofit = Retrofit.Builder()
                .baseUrl(networkSneak.baseAuthApiUrl)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()

            return retrofit.create()
        }

        @Singleton
        @Provides
        fun provideAuthManager(
            simpleStorage: SimpleStorage,
            spotifyAuthService: SpotifyAuthService,
            timeProvider: TimeProvider,
            networkSneak: NetworkSneak,
        ): AuthManager {
            return RealAuthManager(
                simpleStorage = simpleStorage,
                spotifyAuthService = spotifyAuthService,
                timeProvider = timeProvider,
                networkSneak = networkSneak,
            )
        }

        @Singleton
        @Provides
        fun provideAccessTokenInterceptor(
            authManager: AuthManager,
        ): AccessTokenInterceptor {
            return AccessTokenInterceptor(authManager)
        }

        @Singleton
        @Provides
        fun provideSpotifyService(
            interceptor: AccessTokenInterceptor,
            networkSneak: NetworkSneak,
        ): SpotifyService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val converterFactory = Json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType(),
            )

            val retrofit = Retrofit.Builder()
                .baseUrl(networkSneak.baseApiUrl)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()

            return retrofit.create()
        }

        @Singleton
        @Provides
        fun provideNetworkService(
            spotifyService: SpotifyService,
        ): NetworkService {
            return RealNetworkService(spotifyService)
        }
    }
}
