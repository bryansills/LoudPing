package ninja.bryansills.loudping.network.auth.model

import kotlinx.serialization.Serializable

@Serializable data class RefreshTokenResponse(val refresh_token: String, val expires_in: Int)
