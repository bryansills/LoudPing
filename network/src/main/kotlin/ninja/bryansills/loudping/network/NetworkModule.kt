package ninja.bryansills.loudping.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.app.sneak.BuildSneak
import ninja.bryansills.loudping.network.auth.AccessTokenInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideSpotifyService(
        interceptor: AccessTokenInterceptor,
        buildSneak: BuildSneak
    ): SpotifyService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val converterFactory = Json.asConverterFactory(
            "application/json; charset=UTF8".toMediaType()
        )

        val retrofit = Retrofit.Builder()
            .baseUrl(buildSneak.baseApiUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

        return retrofit.create()
    }
}
