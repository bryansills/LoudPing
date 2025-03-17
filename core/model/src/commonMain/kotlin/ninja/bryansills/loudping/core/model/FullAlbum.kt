package ninja.bryansills.loudping.core.model

import kotlin.time.Duration

data class FullAlbum(
    val spotifyId: String,
    val title: String,
    val trackCount: Int,
    val coverImage: String?,
    val type: AlbumType,
    val totalDuration: Duration,
)

enum class AlbumType { Album, Single, Compilation, Unknown }

val FullAlbum.isFullySynced: Boolean
    get() {
        return type != AlbumType.Unknown && totalDuration.isPositive()
    }
