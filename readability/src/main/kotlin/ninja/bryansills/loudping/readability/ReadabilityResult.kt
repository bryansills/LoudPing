package ninja.bryansills.loudping.readability

import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ReadabilityResult(
    val title: String?,
    val content: String?,
    val textContent: String?,
    val length: Int?,
    val excerpt: String?,
    val byline: String?,
    val dir: String?,
    val siteName: String?,
    val lang: String?,
    val publishedTime: Instant?,
)
