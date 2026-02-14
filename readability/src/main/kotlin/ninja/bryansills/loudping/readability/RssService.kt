package ninja.bryansills.loudping.readability

import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {
    @GET
    suspend fun getFeed(@Url url: String): RssResponse
}

@XmlRootElement(name = "rss")
data class RssResponse(
    @XmlElement(required = true)
    var channel: RssChannel = RssChannel(),
)

data class RssChannel(
    @XmlElement(required = true)
    var title: String = "",
    @XmlElement(required = true)
    var description: String = "",
    @XmlElement(name = "item")
    var item: MutableList<RssItem> = mutableListOf(),
)

data class RssItem(
    @XmlElement(required = true)
    var title: String = "",
    @XmlElement(required = true)
    var link: String = "",
    @XmlElement(required = true)
    var pubDate: String = "",
)
