package ninja.bryansills.loudping.html.digest

data class Feed(
  val name: String,
  val url: String,
  val backend: PublicationBackend,
  val format: ArticleFormat,
)

sealed interface PublicationBackend {
  data object Rss : PublicationBackend

  data object GraphQl : PublicationBackend
}

sealed interface ArticleFormat {
  data object Regular : ArticleFormat

  data class Review(val options: ReviewParsingOptions) : ArticleFormat
}

data object RegularParsingOptions

data class ReviewParsingOptions(
  /**
   * Right now just for Pitchfork album reviews and indicates fancy work to extract all the relevant
   * info.
   */
  val alternateDomainName: String? = null
)

val feeds =
  listOf(
    Feed(
      name = "Chicago Reader - Features",
      url = "https://chicagoreader.com/category/music/music-feature/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Chicago Reader - Gossip Wolf",
      url = "https://chicagoreader.com/category/music/gossip-wolf/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Chicago Reader - Concert Previews",
      url = "https://chicagoreader.com/category/music/concert-preview/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Chicago Reader - Chicagoans of Note",
      url = "https://chicagoreader.com/category/music/chicagoans-of-note/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Gorilla vs. Bear",
      url = "https://www.gorillavsbear.net/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "The Singles Jukebox",
      url = "https://www.thesinglesjukebox.com/?feed=rss2",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Stereogum - Premature Evaluation",
      url = "https://www.stereogum.com/category/franchises/premature-evaluation/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Stereogum - The 5 Best Songs of the Week",
      url = "https://www.stereogum.com/category/franchises/the-5-best-songs-of-the-week/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Stereogum - Album Of The Week",
      url = "https://stereogum.com/category/franchises/album-of-the-week/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Stereogum - The Number Ones",
      url = "https://www.stereogum.com/category/franchises/the-number-ones/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Stereogum - The Alternative Number Ones",
      url = "https://www.stereogum.com/category/franchises/the-alternative-number-ones/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Pitchfork - Album Reviews",
      url = "https://pitchfork.com/feed/feed-album-reviews/rss",
      backend = PublicationBackend.Rss,
      format =
        ArticleFormat.Review(
          options = ReviewParsingOptions(alternateDomainName = "sspitchfork.com")
        ),
    ),
    Feed(
      name = "Pitchfork - Track Reviews",
      url = "https://pitchfork.com/feed/feed-track-reviews/rss",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Stereogum - New Music",
      url = "https://www.stereogum.com/category/franchises/music/feed/",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
    ),
    Feed(
      name = "Pitchfork - News",
      url = "https://pitchfork.com/feed/feed-news/rss",
      backend = PublicationBackend.Rss,
      format = ArticleFormat.Regular,
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
