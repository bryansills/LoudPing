package ninja.bryansills.loudping.network.rate

import okhttp3.Interceptor
import okhttp3.Response

class RateLimitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}
