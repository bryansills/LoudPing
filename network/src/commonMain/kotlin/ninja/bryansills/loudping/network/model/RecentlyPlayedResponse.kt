package ninja.bryansills.loudping.network.model

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.recent.PlayHistoryItem
import ninja.bryansills.loudping.network.model.recent.RecentlyPlayedCursors

@Serializable
data class RecentlyPlayedResponse(
    val href: String,
    val next: String,
    val cursors: RecentlyPlayedCursors,
    val limit: Int,
    val total: Int? = -1,
    val items: List<PlayHistoryItem>,
)
