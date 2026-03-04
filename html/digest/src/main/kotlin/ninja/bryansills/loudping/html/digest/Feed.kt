package ninja.bryansills.loudping.html.digest

data class Feed(
    val name: String,
    val url: String,
    val type: FeedType,
)

sealed interface FeedType {
    data object Rss : FeedType
    data object GraphQl : FeedType
}

val smallFeeds = listOf(
    Feed(
        name = "Stereogum - New Music",
        url = "https://www.stereogum.com/category/franchises/music/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Stereogum - Premature Evaluation",
        url = "https://www.stereogum.com/category/franchises/premature-evaluation/feed/",
        type = FeedType.Rss,
    ),
)

val feeds = listOf(
    Feed(
        name = "Stereogum - New Music",
        url = "https://www.stereogum.com/category/franchises/music/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Stereogum - Premature Evaluation",
        url = "https://www.stereogum.com/category/franchises/premature-evaluation/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Stereogum - The 5 Best Songs of the Week",
        url = "https://www.stereogum.com/category/franchises/the-5-best-songs-of-the-week/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Stereogum - Album Of The Week",
        url = "https://stereogum.com/category/franchises/album-of-the-week/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Stereogum - The Number Ones",
        url = "https://www.stereogum.com/category/franchises/the-number-ones/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Stereogum - The Alternative Number Ones",
        url = "https://www.stereogum.com/category/franchises/the-alternative-number-ones/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Pitchfork - Album Reviews",
        url = "https://pitchfork.com/feed/feed-album-reviews/rss",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Pitchfork - Track Reviews",
        url = "https://pitchfork.com/feed/feed-track-reviews/rss",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Pitchfork - News",
        url = "https://pitchfork.com/feed/feed-news/rss",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Gorilla vs. Bear",
        url = "https://www.gorillavsbear.net/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "The Singles Jukebox",
        url = "https://www.thesinglesjukebox.com/?feed=rss2",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Chicago Reader - Features",
        url = "https://chicagoreader.com/category/music/music-feature/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Chicago Reader - Gossip Wolf",
        url = "https://chicagoreader.com/category/music/gossip-wolf/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Chicago Reader - Concert Previews",
        url = "https://chicagoreader.com/category/music/concert-preview/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Chicago Reader - Chicagoans of Note",
        url = "https://chicagoreader.com/category/music/chicagoans-of-note/feed/",
        type = FeedType.Rss,
    ),
    Feed(
        name = "Resident Advisor - Albums",
        url = "https://ra.co/reviews/albums",
        type = FeedType.GraphQl,
    ),
    Feed(
        name = "Resident Advisor - Singles",
        url = "https://ra.co/reviews/singles",
        type = FeedType.GraphQl,
    ),
    Feed(
        name = "Resident Advisor - Mix of the day",
        url = "https://ra.co/reviews/mix-of-the-day",
        type = FeedType.GraphQl,
    ),
)
