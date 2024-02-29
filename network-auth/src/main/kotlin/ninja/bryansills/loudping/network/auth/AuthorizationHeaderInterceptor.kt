package ninja.bryansills.loudping.network.auth

import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import ninja.bryansills.loudping.app.sneak.BuildSneak
import okhttp3.Interceptor
import okhttp3.Response

@OptIn(ExperimentalEncodingApi::class)
class AuthorizationHeaderInterceptor @Inject constructor(
    private val buildSneak: BuildSneak,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rawValue = "${buildSneak.clientId}:${buildSneak.clientSecret}".toByteArray()
        val encodedValue = Base64.encode(rawValue)

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Basic $encodedValue")
            .build()

        return chain.proceed(request)
    }
}
