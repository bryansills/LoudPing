package ninja.bryansills.loudping.html.digest

import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Instant
import kotlin.time.toKotlinInstant
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {
  @GET suspend fun getFeed(@Url url: String): RawResponse
}

@XmlRootElement(name = "rss")
data class RawResponse(
  @JvmField @field:XmlElement(required = true) var channel: RawChannel = RawChannel()
)

data class RawChannel(
  @JvmField @field:XmlElement(required = true) var title: String = "",
  @JvmField @field:XmlElement(required = true) var description: String = "",
  @JvmField @field:XmlElement(name = "item") var item: MutableList<RawItem> = mutableListOf(),
)

data class RawItem(
  @JvmField @field:XmlElement(required = true) var title: String = "",
  @JvmField @field:XmlElement(required = true) var link: String = "",
  @JvmField @field:XmlElement(required = true) var pubDate: String = "",
  @JvmField
  @field:XmlElement(name = "dc:creator", nillable = true, required = false)
  var author: String? = "",
)

data class RssFeed(val title: String, val description: String, val items: List<RssItem>)

data class RssItem(val title: String, val link: String, val pubDate: Instant, val author: String?)

internal fun RawResponse.cleanIt(): RssFeed =
  RssFeed(
    title = this.channel.title,
    description = this.channel.description,
    items = this.channel.item.map { it.cleanIt() },
  )

internal fun RawItem.cleanIt(): RssItem =
  RssItem(
    title = this.title,
    link = this.link,
    pubDate = this.pubDate.rfc1123Instant(),
    author = this.author?.takeIf { it.isNotBlank() },
  )

private fun String.rfc1123Instant(): Instant {
  val zoned = ZonedDateTime.parse(this, DateTimeFormatter.RFC_1123_DATE_TIME)
  return zoned.toInstant().toKotlinInstant()
}
