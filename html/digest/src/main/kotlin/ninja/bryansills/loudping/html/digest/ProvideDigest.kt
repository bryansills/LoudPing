package ninja.bryansills.loudping.html.digest

import ninja.bryansills.loudping.html.core.ProvidesHtmlScope
import ninja.bryansills.loudping.time.TimeProvider
import okio.buffer

suspend fun ProvidesHtmlScope.provideDigest(
    feeds: List<Feed>,
    rssService: RssService,
    readabilityService: ReadabilityService,
    timeProvider: TimeProvider,
) {
    val allTheData = feeds.associateWith { feedDetails ->
        val rssFeed = rssService.getFeed(feedDetails.url)
        rssFeed
            .channel
            .item
            .take(5) // TODO: REMOVE!
            .associateWith { rssItem ->
                readabilityService.getArticle(rssItem.link)
            }
    }

    fileSystem.createDirectories("digest".toPath())
    val dailyPage = generateDigest(timeProvider.now, allTheData)
    fileSystem.sink("digest/index.html".toPath()).buffer().use { sink ->
        sink.writeUtf8(dailyPage)
    }
}
