package ninja.bryansills.loudping.network.rate

import kotlin.time.Duration.Companion.seconds
import okhttp3.Interceptor
import okhttp3.Response

class RateLimitInterceptor(
    private val rateLimiter: RateLimiter,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val permitAcquired = rateLimiter.tryAcquire(permitsRequested = 1, timeout = 30.seconds)
    check(permitAcquired) {
      "Rate limit reached. Next request allowed at ${rateLimiter.blockedUntil}."
    }
    val response = chain.proceed(chain.request())

    return if (!response.isSuccessful && response.code == 429) {
      val headers = response.headers
      headers.forEach { indHeader ->
        println("Header key: ${indHeader.first} value: ${indHeader.second}")
      }
      // TODO: update blocked

      val backupPermitAcquired = rateLimiter.tryAcquire(permitsRequested = 1, timeout = 30.seconds)
      check(backupPermitAcquired) {
        "Rate limit reached. Next request allowed at ${rateLimiter.blockedUntil}."
      }
      chain.proceed(chain.request())
    } else {
      response
    }
  }
}
