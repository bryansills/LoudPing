package ninja.bryansills.loudping.html.builder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.coroutines.launchBlocking
import ninja.bryansills.loudping.html.callback.provideCallback
import ninja.bryansills.loudping.html.core.DefaultProvidesHtmlScope
import ninja.bryansills.loudping.html.core.provideRoot
import ninja.bryansills.loudping.html.digest.DefaultDigestBuilder
import ninja.bryansills.loudping.html.digest.DefaultFeedItemBuilder
import ninja.bryansills.loudping.html.digest.DefaultFeedItemDataFetcher
import ninja.bryansills.loudping.html.digest.DefaultReadabilityService
import ninja.bryansills.loudping.html.digest.RssService
import ninja.bryansills.loudping.html.digest.feeds
import ninja.bryansills.loudping.html.digest.provideDigest
import ninja.bryansills.loudping.time.RealTimeProvider
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.htmlunit.WebClient
import retrofit2.Retrofit
import retrofit2.converter.jaxb3.JaxbConverterFactory
import retrofit2.create

fun main() {
  val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  val provideHtmlScope = DefaultProvidesHtmlScope(basePath = "build/html")

  val retrofit =
    Retrofit.Builder()
      .baseUrl("https://buttz.mcghee/".toHttpUrl())
      .addConverterFactory(JaxbConverterFactory.create())
      .build()
  val rssService = retrofit.create<RssService>()
  val webClient = WebClient()
  webClient.options.isThrowExceptionOnScriptError = false
  val readabilityService = DefaultReadabilityService(webClient = webClient, json = Json)
  val feedItemDataFetcher = DefaultFeedItemDataFetcher(readabilityService = readabilityService)
  val feedItemBuilder = DefaultFeedItemBuilder()
  val timeProvider = RealTimeProvider()
  val digestBuilder =
    DefaultDigestBuilder(
      feedMetadataList = feeds,
      rssService = rssService,
      timeProvider = timeProvider,
      feedItemDataFetcher = feedItemDataFetcher,
      feedItemBuilder = feedItemBuilder,
    )

  mainScope.launchBlocking {
    with(provideHtmlScope) {
      provideRoot()
      provideCallback()
      provideDigest(
        digestBuilder = digestBuilder,
        timeProvider = timeProvider,
      )
    }
  }
}
