package ninja.bryansills.loudping.core.model

import kotlin.time.Duration

data class Track(
    val spotifyId: String,
    val title: String,
    val trackNumber: Int,
    val discNumber: Int,
    val duration: Duration,
    val album: Album,
    val artists: List<Artist>,
) {
    companion object {
        const val MISSING_DISC_NUMBER = -1
        val MISSING_DURATION = Duration.ZERO
    }
}
