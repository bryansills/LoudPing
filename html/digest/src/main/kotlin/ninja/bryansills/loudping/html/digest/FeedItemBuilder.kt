package ninja.bryansills.loudping.html.digest

interface FeedItemBuilder {
  fun create(
    feed: Feed,
    rssItem: RssItem,
    cleanedData: Map<ItemProcessingMethod, ReadabilityResult?>,
  ): FeedItem
}

class DefaultFeedItemBuilder : FeedItemBuilder {
  override fun create(
    feed: Feed,
    rssItem: RssItem,
    cleanedData: Map<ItemProcessingMethod, ReadabilityResult?>,
  ): FeedItem {
    val title = cleanedData.entries.firstNotNullOfOrNull { (_, cleaned) -> cleaned?.title }
    val timestamp =
      cleanedData.entries.firstNotNullOfOrNull { (_, cleaned) -> cleaned?.publishedTime }
    val author = cleanedData.entries.firstNotNullOfOrNull { (_, cleaned) -> cleaned?.byline }
    val contents = cleanedData.entries.firstNotNullOfOrNull { (_, cleaned) -> cleaned?.content }

    return when (feed.outputStyle) {
      OutputStyle.Regular -> {
        FeedItem.Article(
          url = rssItem.link,
          title = title ?: rssItem.title,
          timestamp = timestamp ?: rssItem.pubDate,
          author = author ?: rssItem.author,
          contents = contents,
        )
      }
      OutputStyle.Review -> {
        FeedItem.Review(
          url = rssItem.link,
          title = title ?: rssItem.title,
          artist = "TODO()",
          timestamp = timestamp ?: rssItem.pubDate,
          author = author ?: rssItem.author,
          contents = contents,
        )
      }
    }
  }
}
