package ninja.bryansills.loudping.network.auth

import com.slack.eithernet.ApiResult
import ninja.bryansills.loudping.network.auth.model.AccessTokenResponse
import ninja.bryansills.loudping.network.auth.model.RefreshTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotifyAuthService {
  @FormUrlEncoded
  @POST("/api/token")
  suspend fun requestTokens(
      @Field("grant_type") grantType: String,
      @Field("code") code: String,
      @Field("redirect_uri") redirectUri: String,
  ): ApiResult<RefreshTokenResponse, Unit>

  @FormUrlEncoded
  @POST("/api/token")
  suspend fun refreshTokens(
      @Field("grant_type") grantType: String,
      @Field("refresh_token") refreshToken: String,
      @Field("client_id") clientId: String,
  ): ApiResult<AccessTokenResponse, Unit>
}
