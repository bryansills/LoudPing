package ninja.bryansills.loudping.network.model.recent

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PlayHistoryItem(
    val played_at: Instant,
    val context: Context,
    val track: PlayHistoryTrack,
) {
    @Serializable
    data class Context(
        val type: String,
        val href: String,
        val uri: String,
    )
}
