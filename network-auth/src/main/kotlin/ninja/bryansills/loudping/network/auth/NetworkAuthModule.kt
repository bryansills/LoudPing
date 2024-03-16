package ninja.bryansills.loudping.network.auth

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.app.sneak.BuildSneak
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@InstallIn(SingletonComponent::class)
@Module
interface NetworkAuthModule {
    @Binds
    fun bindTokenManager(realTokenManager: RealAuthManager): AuthManager

    companion object {
        @Provides
        fun provideSpotifyAuthService(
            authorizationHeaderInterceptor: AuthorizationHeaderInterceptor,
            buildSneak: BuildSneak,
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
                .baseUrl(buildSneak.baseAuthApiUrl)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()

            return retrofit.create()
        }
    }
}
