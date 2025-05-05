package ninja.bryansills.loudping.core.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

data class FullAlbum(
    val spotifyId: String,
    val title: String,
    val trackCount: Int,
    val coverImage: String?,
    val type: AlbumType,
    val artists: List<Artist>,
    val tracks: List<AlbumTrack>,
)

enum class AlbumType { Album, Single, Compilation, Unknown }

val FullAlbum.isFullySynced: Boolean
    get() {
        return type != AlbumType.Unknown && artists.isNotEmpty() && tracks.isNotEmpty() && totalDuration.isPositive()
    }

data class AlbumTrack(
    val spotifyId: String,
    val title: String,
    val trackNumber: Int,
    val discNumber: Int,
    val duration: Duration,
)

val FullAlbum.totalDuration: Duration
    get() {
        return if (this.tracks.isEmpty()) {
            Duration.ZERO
        } else {
            this.tracks.sumOf { it.duration.inWholeMilliseconds }.milliseconds
        }
    }
