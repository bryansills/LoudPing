package ninja.bryansills.loudping.database.model

data class Album(
    val spotifyId: String,
    val title: String,
    val trackCount: Int,
    val coverImage: String?,
)
