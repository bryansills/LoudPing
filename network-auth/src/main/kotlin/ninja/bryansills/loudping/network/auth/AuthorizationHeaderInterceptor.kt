package ninja.bryansills.loudping.network.auth

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlin.io.encoding.Base64
import ninja.bryansills.loudping.sneak.network.NetworkSneak
import okhttp3.Interceptor
import okhttp3.Response

@ContributesBinding(AppScope::class)
@Inject
class AuthorizationHeaderInterceptor(private val networkSneak: NetworkSneak) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val rawValue = "${networkSneak.clientId}:${networkSneak.clientSecret}".toByteArray()
    val encodedValue = Base64.encode(rawValue)

    val request =
      chain.request().newBuilder().addHeader("Authorization", "Basic $encodedValue").build()

    return chain.proceed(request)
  }
}
