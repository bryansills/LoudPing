package ninja.bryansills.loudping.readability

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.coroutines.launchBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.htmlunit.WebClient
import retrofit2.Retrofit
import retrofit2.converter.jaxb3.JaxbConverterFactory
import retrofit2.create

fun main() {
    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://buttz.mcghee/".toHttpUrl())
        .addConverterFactory(JaxbConverterFactory.create())
        .build()
    val rssService = retrofit.create<RssService>()
    val webClient = WebClient()
    webClient.options.isThrowExceptionOnScriptError = false
    val readabilityService = DefaultReadabilityService(webClient = webClient, json = Json)

    mainScope.launchBlocking {
        val allTheData = feeds.associateWith { feedDetails ->
            val rssFeed = rssService.getFeed(feedDetails.url)
            rssFeed.channel.item.associateWith { rssItem ->
                readabilityService.getArticle(rssItem.link)
            }
        }
        println(allTheData)
    }
}

private data class Feed(
    val name: String,
    val url: String,
)

private val feeds = listOf(
    Feed(
        name = "Stereogum - The 5 Best Songs of the Week",
        url = "https://www.stereogum.com/category/franchises/the-5-best-songs-of-the-week/feed/",
    ),
    Feed(
        name = "Pitchfork - Album Reviews",
        url = "https://pitchfork.com/feed/feed-album-reviews/rss",
    ),
    Feed(
        name = "Pitchfork - Track Reviews",
        url = "https://pitchfork.com/feed/feed-track-reviews/rss",
    ),
)
