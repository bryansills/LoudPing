package ninja.bryansills.loudping.network.auth.model

import kotlinx.serialization.Serializable

@Serializable data class AccessTokenResponse(val access_token: String, val expires_in: Int)
