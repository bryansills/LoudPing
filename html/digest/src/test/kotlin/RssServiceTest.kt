package ninja.bryansills.loudping.html.digest

import app.cash.burst.InterceptTest
import app.cash.burst.coroutines.CoroutineTestInterceptor
import kotlin.test.Test
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.test.runTest
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.jaxb3.JaxbConverterFactory
import retrofit2.create

class RssServiceTest {
    @InterceptTest
    val retryInterceptor = CoroutineTestInterceptor { testFunction ->
        println("starting to retry")
        var failureCount = 0
        val maxAttempts = 10

        for (attempt in 0..maxAttempts) {
            try {
                testFunction()

                // we succeeded! no need to keep on trying. early return!
                return@CoroutineTestInterceptor
            } catch (throwable: Throwable) {
                testFunction.scope.ensureActive()

                failureCount++
                println("Test attempt #$attempt out of $maxAttempts failed. Reason: ${throwable.message}")
                if (failureCount == maxAttempts) {
                    // they all failed! :(
                    println("Failed too many times!")
                    throw throwable
                }
            }
        }
    }

    @Test
    fun `just a simple test to make sure i didn't break anything`() = runTest {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://buttz.mcghee/".toHttpUrl())
            .addConverterFactory(JaxbConverterFactory.create())
            .build()
        val rssService = retrofit.create<RssService>()

        val networkResult = rssService.getFeed(feeds.first().url)

        assert(networkResult.channel.item.isNotEmpty()) { "The feed has some articles!" }
    }
}
