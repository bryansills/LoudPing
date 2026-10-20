package ninja.bryansills.loudping.html.digest

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

interface FeedItemDataFetcher {
  fun getData(feed: Feed, rssItem: RssItem): FeedItemData?
}

data class FeedItemData(
  val rssItem: RssItem,
  val readabilityItem: ReadabilityResult?,
)

class DefaultFeedItemDataFetcher(private val readabilityService: ReadabilityService) :
  FeedItemDataFetcher {
  override fun getData(feed: Feed, rssItem: RssItem): FeedItemData? {
    val itemUrl = getItemUrl(feed, rssItem)
    val readabilityItem = readabilityService.getArticle(itemUrl)
    return FeedItemData(rssItem, readabilityItem)
  }
}

private fun getItemUrl(feed: Feed, rssItem: RssItem): String {
  return when (feed.format) {
    ArticleFormat.Regular -> rssItem.link
    is ArticleFormat.Review -> {
      if (feed.format.options.alternateHostName != null) {
        val rssUrl = rssItem.link.toHttpUrl()
        val swappedUrl = rssUrl.build {
          host(feed.format.options.alternateHostName)
        }
        swappedUrl.toString()
      } else {
        rssItem.link
      }
    }
  }
}

private inline fun HttpUrl.build(action: HttpUrl.Builder.() -> Unit): HttpUrl {
  return this.newBuilder().apply(action).build()
}
