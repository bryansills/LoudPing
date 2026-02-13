package ninja.bryansills.loudping.readability

import kotlinx.serialization.json.Json
import org.htmlunit.WebClient

fun main() {
    println("hello readability")
    val webClient = WebClient()
    webClient.options.isThrowExceptionOnScriptError = false
    val readabilityService = DefaultReadabilityService(webClient = webClient, json = Json)
//    val page: HtmlPage = webClient.getPage("https://stereogum.com/2488104/the-5-best-songs-of-the-week-612/lists/the-5-best-songs-of-the-week")
//    println("got page")
//
//    val article = page.executeJavaScript(ReadabilityJs).javaScriptResult as String
//    println("Here is the article:")
//    println(article)
    val article = readabilityService.getArticle("https://stereogum.com/2488104/the-5-best-songs-of-the-week-612/lists/the-5-best-songs-of-the-week")
    println("Here is the article: $article")
}
