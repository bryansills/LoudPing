package ninja.bryansills.loudping.html.digest

import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.html.body
import kotlinx.html.details
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.summary
import kotlinx.html.title
import kotlinx.html.unsafe
import ninja.bryansills.loudping.html.core.buildHtml
import ninja.bryansills.loudping.html.core.commonHeadAttributes

internal fun generateDigest(
    postingDate: Instant,
    allTheData: Map<Feed, Map<RssItem, ReadabilityResult?>>,
    timeZone: TimeZone = TimeZone.of("America/Chicago")
) = buildHtml {
    val formattedDate = postingDate.toLocalDateTime(timeZone).format(headlineFormat)

    head {
        commonHeadAttributes()
        meta { charset = "UTF-8" }
        title {
            +"$formattedDate Music"
        }
        link {
            rel = "stylesheet"
            href = "https://cdn.jsdelivr.net/npm/normalize.css@8.0.1/normalize.css"
        }
        link {
            rel = "stylesheet"
            href = "/assets/css/global.css"
        }
    }
    body(classes = "post flow") {
        h1 { +"All the good music for $formattedDate" }

        allTheData.entries.forEach { (feed, feedItems) ->
            h2 { +"Feed: ${feed.name}" }

            feedItems.entries.forEach { (rssItem, readabilityItem) ->
                details {
                    summary {
                        h3 { +rssItem.title }
                    }
                    unsafe {
                        raw(readabilityItem?.content ?: "Missing article contents")
                    }
                }
            }
        }
    }
}

val headlineFormat = LocalDateTime.Format {
    dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
    char(',')
    char(' ')
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    day()
}
