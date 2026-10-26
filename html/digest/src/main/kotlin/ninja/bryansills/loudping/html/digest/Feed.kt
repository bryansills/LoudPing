package ninja.bryansills.loudping.html.digest

data class Feed(
  val name: String,
  val backend: PublicationBackend,
  val itemProcessingMethods: List<ItemProcessingMethod> = listOf(ItemProcessingMethod.HtmlUnit()),
  val outputStyle: OutputStyle,
)

sealed interface PublicationBackend {
  val url: String

  data class Rss(override val url: String) : PublicationBackend

  data class GraphQl(override val url: String) : PublicationBackend
}

enum class OutputStyle {
  Regular,
  Review,
}

sealed interface ItemProcessingMethod {
  data object RssOnly : ItemProcessingMethod

  data class HtmlUnit(val alternateHostName: String? = null) : ItemProcessingMethod

  data class KSoup(val alternateHostName: String? = null) : ItemProcessingMethod
}

val feeds =
  listOf(
    Feed(
      name = "Chicago Reader - Features",
      backend =
        PublicationBackend.Rss(
          url = "https://chicagoreader.com/category/music/music-feature/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Chicago Reader - Gossip Wolf",
      backend =
        PublicationBackend.Rss(url = "https://chicagoreader.com/category/music/gossip-wolf/feed/"),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Chicago Reader - Concert Previews",
      backend =
        PublicationBackend.Rss(
          url = "https://chicagoreader.com/category/music/concert-preview/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Chicago Reader - Chicagoans of Note",
      backend =
        PublicationBackend.Rss(
          url = "https://chicagoreader.com/category/music/chicagoans-of-note/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Gorilla vs. Bear",
      backend = PublicationBackend.Rss(url = "https://www.gorillavsbear.net/feed/"),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "The Singles Jukebox",
      backend = PublicationBackend.Rss(url = "https://www.thesinglesjukebox.com/?feed=rss2"),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Stereogum - Premature Evaluation",
      backend =
        PublicationBackend.Rss(
          url = "https://www.stereogum.com/category/franchises/premature-evaluation/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Stereogum - The 5 Best Songs of the Week",
      backend =
        PublicationBackend.Rss(
          url = "https://www.stereogum.com/category/franchises/the-5-best-songs-of-the-week/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Stereogum - Album Of The Week",
      backend =
        PublicationBackend.Rss(
          url = "https://stereogum.com/category/franchises/album-of-the-week/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Stereogum - The Number Ones",
      backend =
        PublicationBackend.Rss(
          url = "https://www.stereogum.com/category/franchises/the-number-ones/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Stereogum - The Alternative Number Ones",
      backend =
        PublicationBackend.Rss(
          url = "https://www.stereogum.com/category/franchises/the-alternative-number-ones/feed/"
        ),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Pitchfork - Album Reviews",
      backend = PublicationBackend.Rss(url = "https://pitchfork.com/feed/feed-album-reviews/rss"),
      itemProcessingMethods =
        listOf(
          ItemProcessingMethod.HtmlUnit(),
          ItemProcessingMethod.HtmlUnit(alternateHostName = "sspitchfork.com"),
        ),
      outputStyle = OutputStyle.Review,
    ),
    Feed(
      name = "Pitchfork - Track Reviews",
      backend = PublicationBackend.Rss(url = "https://pitchfork.com/feed/feed-track-reviews/rss"),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Stereogum - New Music",
      backend =
        PublicationBackend.Rss(url = "https://www.stereogum.com/category/franchises/music/feed/"),
      outputStyle = OutputStyle.Regular,
    ),
    Feed(
      name = "Pitchfork - News",
      backend = PublicationBackend.Rss(url = "https://pitchfork.com/feed/feed-news/rss"),
      outputStyle = OutputStyle.Regular,
    ),
    //    Feed(
    //        name = "Resident Advisor - Albums",
    //        url = "https://ra.co/reviews/albums",
    //        type = FeedType.GraphQl,
    //    ),
    //    Feed(
    //        name = "Resident Advisor - Singles",
    //        url = "https://ra.co/reviews/singles",
    //        type = FeedType.GraphQl,
    //    ),
    //    Feed(
    //        name = "Resident Advisor - Mix of the day",
    //        url = "https://ra.co/reviews/mix-of-the-day",
    //        type = FeedType.GraphQl,
    //    ),
  )

val testingFields =
  listOf(
    Feed(
      name = "Pitchfork - Album Reviews",
      backend = PublicationBackend.Rss(url = "https://pitchfork.com/feed/feed-album-reviews/rss"),
      itemProcessingMethods =
        listOf(
          ItemProcessingMethod.HtmlUnit(),
          ItemProcessingMethod.HtmlUnit(alternateHostName = "sspitchfork.com"),
        ),
      outputStyle = OutputStyle.Review,
    ),
    Feed(
      name = "Pitchfork - Track Reviews",
      backend = PublicationBackend.Rss(url = "https://pitchfork.com/feed/feed-track-reviews/rss"),
      outputStyle = OutputStyle.Regular,
    ),
  )
