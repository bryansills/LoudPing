package ninja.bryansills.loudping.html.digest

interface FeedItemBuilder {
  fun create(
    feed: Feed,
    rssItem: RssItem,
    readabilityItem: ReadabilityResult?,
  ): FeedItem
}

class DefaultFeedItemBuilder : FeedItemBuilder {
  override fun create(feed: Feed, rssItem: RssItem, readabilityItem: ReadabilityResult?): FeedItem {
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
}
