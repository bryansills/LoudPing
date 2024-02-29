package ninja.bryansills.loudping.network.model.recent

import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayedCursors(
    val before: String,
    val after: String
)
