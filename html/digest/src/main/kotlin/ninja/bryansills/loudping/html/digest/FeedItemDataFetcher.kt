package ninja.bryansills.loudping.html.digest

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

interface FeedItemDataFetcher {
  fun getData(itemUrl: String, processingMethod: ItemProcessingMethod): ReadabilityResult?
}

class DefaultFeedItemDataFetcher(private val readabilityService: ReadabilityService) :
  FeedItemDataFetcher {
  override fun getData(
    itemUrl: String,
    processingMethod: ItemProcessingMethod,
  ): ReadabilityResult? {
    return when (processingMethod) {
      ItemProcessingMethod.RssOnly -> null
      is ItemProcessingMethod.HtmlUnit -> {
        val formattedUrl = processingMethod.formatUrl(itemUrl)
        readabilityService.getArticle(formattedUrl)
      }
      is ItemProcessingMethod.KSoup -> null
    }
  }
}

private fun ItemProcessingMethod.formatUrl(itemUrl: String): String {
  val alternateHostName =
    when (this) {
      is ItemProcessingMethod.HtmlUnit -> this.alternateHostName
      is ItemProcessingMethod.KSoup -> this.alternateHostName
      ItemProcessingMethod.RssOnly -> null
    }

  return if (alternateHostName == null) {
    itemUrl
  } else {
    val rssUrl = itemUrl.toHttpUrl()
    val swappedUrl = rssUrl.build {
      host(alternateHostName)
    }
    swappedUrl.toString()
  }
}

private inline fun HttpUrl.build(action: HttpUrl.Builder.() -> Unit): HttpUrl {
  return this.newBuilder().apply(action).build()
}
