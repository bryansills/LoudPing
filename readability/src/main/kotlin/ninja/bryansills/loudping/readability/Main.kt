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
    retrofitStuff()
//    readabilityStuff()
}

private fun retrofitStuff() {
    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    mainScope.launchBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://buttz.mcghee/".toHttpUrl())
            .addConverterFactory(JaxbConverterFactory.create())
            .build()
        val service = retrofit.create<RssService>()
        val response = service.getFeed("https://www.stereogum.com/category/franchises/the-5-best-songs-of-the-week/feed/")
        println(response)
    }
}

private fun readabilityStuff() {
    println("hello readability")
    val webClient = WebClient()
    webClient.options.isThrowExceptionOnScriptError = false
    val readabilityService = DefaultReadabilityService(webClient = webClient, json = Json)
    val article = readabilityService.getArticle("https://stereogum.com/2488104/the-5-best-songs-of-the-week-612/lists/the-5-best-songs-of-the-week")
    println("Here is the article: $article")
}
