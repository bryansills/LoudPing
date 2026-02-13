package ninja.bryansills.loudping.readability

import kotlinx.serialization.json.Json
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlPage

interface ReadabilityService {
    fun getArticle(url: String): ReadabilityResult?
}

class DefaultReadabilityService(
    private val webClient: WebClient,
    private val json: Json
) : ReadabilityService {
    override fun getArticle(url: String): ReadabilityResult? {
        val page = webClient.getPage<HtmlPage>(url)
        val article = page.executeJavaScript(ReadabilityJs).javaScriptResult as String
        return json.decodeFromString(article)
    }
}
