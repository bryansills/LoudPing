package ninja.bryansills.loudping.network.di

import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.network.SpotifyService
import ninja.bryansills.loudping.network.auth.AccessTokenInterceptor
import ninja.bryansills.loudping.network.auth.AuthorizationHeaderInterceptor
import ninja.bryansills.loudping.network.auth.SpotifyAuthService
import ninja.bryansills.loudping.sneak.network.NetworkSneak
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@ContributesTo(AppScope::class)
@BindingContainer
object NetworkModule {
  @SingleIn(AppScope::class) @Provides fun provideJson(): Json = Json { ignoreUnknownKeys = true }

  @SingleIn(AppScope::class) @Provides fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

  @SingleIn(AppScope::class)
  @Provides
  fun provideSpotifyAuthService(
    json: Json,
    okHttpClient: OkHttpClient,
    authorizationHeaderInterceptor: AuthorizationHeaderInterceptor,
    networkSneak: NetworkSneak,
  ): SpotifyAuthService {
    val okHttpClient =
      okHttpClient.newBuilder().addInterceptor(authorizationHeaderInterceptor).build()
    val converterFactory = json.asConverterFactory("application/json; charset=UTF8".toMediaType())

    val retrofit =
      Retrofit.Builder()
        .baseUrl(networkSneak.baseAuthApiUrl)
        .client(okHttpClient)
        .addConverterFactory(ApiResultConverterFactory)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(ApiResultCallAdapterFactory)
        .build()

    return retrofit.create()
  }

  @SingleIn(AppScope::class)
  @Provides
  fun provideSpotifyService(
    interceptor: AccessTokenInterceptor,
    json: Json,
    okHttpClient: OkHttpClient,
    networkSneak: NetworkSneak,
  ): SpotifyService {
    val okHttpClient = okHttpClient.newBuilder().addInterceptor(interceptor).build()
    val converterFactory = json.asConverterFactory("application/json; charset=UTF8".toMediaType())

    val retrofit =
      Retrofit.Builder()
        .baseUrl(networkSneak.baseApiUrl)
        .client(okHttpClient)
        .addConverterFactory(ApiResultConverterFactory)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(ApiResultCallAdapterFactory)
        .build()

    return retrofit.create()
  }
}
