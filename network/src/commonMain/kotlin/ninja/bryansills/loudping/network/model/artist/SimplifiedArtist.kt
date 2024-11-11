package ninja.bryansills.loudping.network.model.artist

import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedArtist(
    val id: String,
    val name: String,
)
