package ninja.bryansills.loudping.html.digest

import kotlin.time.Duration.Companion.days
import ninja.bryansills.loudping.html.core.ProvidesHtmlScope
import ninja.bryansills.loudping.time.TimeProvider
import okio.buffer

suspend fun ProvidesHtmlScope.provideDigest(
    feeds: List<Feed>,
    rssService: RssService,
    readabilityService: ReadabilityService,
    timeProvider: TimeProvider,
) {
    val fullFeeds = feeds.associateWith { details ->
        details to rssService.getFeed(details.url).cleanIt()
    }
    val today = timeProvider.now
    val yesterday = today - 1.days
    val fullData = fullFeeds
        .values
        .associate { (details, feeds) ->
            val feedsWithRead = feeds.items
                .filter { it.pubDate > yesterday }
                .associateWith { rssItem ->
                    readabilityService.getArticle(rssItem.link)
                }

            details to feedsWithRead
        }
        .filter { (_, fullMap) ->
            fullMap.isNotEmpty()
        }

    fileSystem.createDirectories("digest".buildPath())
    val dailyPage = generateDigest(timeProvider.now, fullData)
    fileSystem.sink("digest/index.html".buildPath()).buffer().use { sink ->
        sink.writeUtf8(dailyPage)
    }
}
