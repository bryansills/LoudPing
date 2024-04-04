package ninja.bryansills.loudping.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PrivateUserResponse(
    val id: String,
    val display_name: String,
)
