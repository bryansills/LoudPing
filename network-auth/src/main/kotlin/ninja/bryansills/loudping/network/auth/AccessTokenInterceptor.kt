package ninja.bryansills.loudping.network.auth

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

@ContributesBinding(AppScope::class)
@Inject
class AccessTokenInterceptor(private val authManager: AuthManager) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val accessToken = runBlocking { authManager.getValidAccessToken() }
    val request =
      chain.request().newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
    return chain.proceed(request)
  }
}
