package ninja.bryansills.loudping.html.digest

import kotlin.time.Duration.Companion.days
import ninja.bryansills.loudping.time.TimeProvider
import okhttp3.HttpUrl.Companion.toHttpUrl

interface DigestBuilder {
  suspend fun build(): Map<Feed, List<FeedItem>>
}

class DefaultDigestBuilder(
  private val feeds: List<Feed>,
  private val rssService: RssService,
  private val readabilityService: ReadabilityService,
  private val timeProvider: TimeProvider,
) : DigestBuilder {
  override suspend fun build(): Map<Feed, List<FeedItem>> {
    val fullFeeds = feeds.associateWith { details ->
      details to rssService.getFeed(details.url).cleanIt()
    }
    val today = timeProvider.now
    val yesterday = today - 1.days
    val fullData =
      fullFeeds.values
        .associate { (details, feeds) ->
          val feedsWithRead =
            feeds.items
              .filter { it.pubDate > yesterday }
              .associateWith { rssItem ->
                val itemUrl = getItemUrl(details, rssItem)
                readabilityService.getArticle(itemUrl)
              }

          details to
            feedsWithRead.map { (rssItem, readabilityItem) ->
              createFeedItem(details, rssItem, readabilityItem)
            }
        }
        .filter { (_, fullMap) -> fullMap.isNotEmpty() }

    return fullData
  }
}

private fun getItemUrl(feed: Feed, rssItem: RssItem): String {
  return when (feed.format) {
    ArticleFormat.Regular -> rssItem.link
    is ArticleFormat.Review -> {
      if (feed.format.options.alternateHostName != null) {
        val rssUrl = rssItem.link.toHttpUrl()
        val swappedUrl = rssUrl.newBuilder().host(feed.format.options.alternateHostName).build()
        swappedUrl.toString()
      } else {
        rssItem.link
      }
    }
  }
}

private fun createFeedItem(
  feed: Feed,
  rssItem: RssItem,
  readabilityItem: ReadabilityResult?,
): FeedItem {
  return when (feed.format) {
    ArticleFormat.Regular -> {
      FeedItem.Article(
        url = rssItem.link,
        title = readabilityItem?.title ?: rssItem.title,
        timestamp = readabilityItem?.publishedTime ?: rssItem.pubDate,
        author = readabilityItem?.byline ?: rssItem.author,
        contents = readabilityItem?.content,
      )
    }
    is ArticleFormat.Review -> {
      FeedItem.Review(
        url = rssItem.link,
        title = readabilityItem?.title ?: rssItem.title,
        artist = "TODO()",
        timestamp = readabilityItem?.publishedTime ?: rssItem.pubDate,
        author = readabilityItem?.byline ?: rssItem.author,
        contents = readabilityItem?.content,
      )
    }
  }
}
