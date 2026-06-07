package ninja.bryansills.loudping.html.digest

import kotlin.time.Instant

sealed interface FeedItem {
  data class Article(
    val url: String,
    val title: String,
    val timestamp: Instant,
    val author: String?,
    val contents: String?,
  ) : FeedItem

  data class Review(
    val url: String,
    val title: String,
    val artist: String,
    val timestamp: Instant,
    val author: String?,
    val contents: String?,
  ) : FeedItem
}
