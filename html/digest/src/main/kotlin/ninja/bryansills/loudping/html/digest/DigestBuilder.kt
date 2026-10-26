package ninja.bryansills.loudping.html.digest

import kotlin.time.Duration.Companion.days
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import ninja.bryansills.loudping.time.TimeProvider

interface DigestBuilder {
  suspend fun build(): Map<Feed, List<FeedItem>>
}

class DefaultDigestBuilder(
  private val feedMetadataList: List<Feed>,
  private val rssService: RssService,
  private val timeProvider: TimeProvider,
  private val feedItemDataFetcher: FeedItemDataFetcher,
  private val feedItemBuilder: FeedItemBuilder,
) : DigestBuilder {
  override suspend fun build(): Map<Feed, List<FeedItem>> {
    val metadataAndData = rssService.getFeeds(feedMetadataList)
    val today = timeProvider.now
    val yesterday = today - 1.days
    val result =
      metadataAndData.entries.associate { (metadata, rssData) ->
        val rssItems = rssData?.items ?: listOf()
        val processedRssData =
          rssItems
            .filter { it.pubDate > yesterday }
            .associateWith { rssItem ->
              metadata.itemProcessingMethods.associateWith { method ->
                feedItemDataFetcher.getData(itemUrl = rssItem.link, processingMethod = method)
              }
            }
        val feedItems =
          processedRssData.entries.map { (rssItem, itemData) ->
            feedItemBuilder.create(metadata, rssItem, itemData)
          }
        metadata to feedItems
      }

    return result.entries
      .filter { (_, feedItems) -> feedItems.isNotEmpty() }
      .associate { (metadata, feedItems) -> metadata to feedItems }
  }
}

private suspend fun RssService.getFeeds(feedMetadataList: List<Feed>): Map<Feed, RssFeed?> {
  return feedMetadataList.associateWith { feedMetadata ->
    try {
      this.getFeed(feedMetadata.backend.url).cleanIt()
    } catch (_: Throwable) {
      currentCoroutineContext().ensureActive()
      null
    }
  }
}
