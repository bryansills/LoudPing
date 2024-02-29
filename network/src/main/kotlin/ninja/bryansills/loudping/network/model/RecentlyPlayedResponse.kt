package ninja.bryansills.loudping.network.model

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.recent.RecentlyPlayedCursors

@Serializable
data class RecentlyPlayedResponse(
    val next: String,
    val cursors: RecentlyPlayedCursors,
    val limit: Int
)
