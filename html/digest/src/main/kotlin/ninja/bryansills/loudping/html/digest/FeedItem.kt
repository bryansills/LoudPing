package ninja.bryansills.loudping.html.digest

import kotlin.time.Instant

sealed interface FeedItem {
  val url: String
  val title: String
  val timestamp: Instant
  val author: String?
  val contents: String?

  data class Article(
    override val url: String,
    override val title: String,
    override val timestamp: Instant,
    override val author: String?,
    override val contents: String?,
  ) : FeedItem

  data class Review(
    override val url: String,
    override val title: String,
    val artist: String,
    override val timestamp: Instant,
    override val author: String?,
    override val contents: String?,
  ) : FeedItem
}
