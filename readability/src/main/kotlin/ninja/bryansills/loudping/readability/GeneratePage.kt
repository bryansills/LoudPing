package ninja.bryansills.loudping.readability

import kotlin.time.Instant
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.details
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.lang
import kotlinx.html.meta
import kotlinx.html.stream.appendHTML
import kotlinx.html.summary
import kotlinx.html.title
import kotlinx.html.unsafe

internal fun generatePage(
    postingDate: Instant,
    allTheData: Map<Feed, Map<RssItem, ReadabilityResult?>>,
) = buildHtml {
    head {
        meta { charset = "UTF-8" }
        title {
            val tempFormattedDate = postingDate.toString()
            +"$tempFormattedDate Music"
        }
    }
    body {
        h1 { +"All the good music for $postingDate" }

        allTheData.entries.forEach { (feed, feedItems) ->
            h2 { +"Feed: ${feed.name}" }

            feedItems.entries.forEach { (rssItem, readabilityItem) ->
                details {
                    summary {
                        +rssItem.title
                    }
                    unsafe {
                        raw(readabilityItem?.content ?: "Missing article contents")
                    }
                }
            }
        }
    }
}

internal fun buildHtml(block: HTML.() -> Unit): String = buildString {
    appendLine("<!DOCTYPE html>")
    appendHTML().html {
        lang = "en"
        block()
    }
}
