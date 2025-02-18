package ninja.bryansills.loudping.network.auth

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import ninja.bryansills.loudping.sneak.network.NetworkSneak
import okhttp3.Interceptor
import okhttp3.Response

@OptIn(ExperimentalEncodingApi::class)
class AuthorizationHeaderInterceptor(
    private val networkSneak: NetworkSneak,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val rawValue = "${networkSneak.clientId}:${networkSneak.clientSecret}".toByteArray()
    val encodedValue = Base64.encode(rawValue)

    val request =
        chain.request().newBuilder().addHeader("Authorization", "Basic $encodedValue").build()

    return chain.proceed(request)
  }
}
