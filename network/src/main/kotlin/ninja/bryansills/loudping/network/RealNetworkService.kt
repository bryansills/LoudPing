package ninja.bryansills.loudping.network

import com.slack.eithernet.ApiResult
import com.slack.eithernet.successOrNothing
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SavedAlbumsResponse
import ninja.bryansills.loudping.network.model.album.FullAlbum
import ninja.bryansills.loudping.network.model.track.Track

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class RealNetworkService(private val spotifyService: SpotifyService) : NetworkService {
  override suspend fun getMe(): ApiResult<PrivateUserResponse, Unit> = spotifyService.getMe()

  override suspend fun getRecentlyPlayed(): ApiResult<RecentlyPlayedResponse, Unit> =
    spotifyService.getRecentlyPlayed()

  override suspend fun getSavedAlbums(): ApiResult<SavedAlbumsResponse, Unit> =
    spotifyService.getSavedAlbums()

  override suspend fun getSeveralTracks(ids: List<String>): ApiResult<List<Track>, Unit> {
    require(ids.size <= 50) { "You can only query for 50 tracks at a time." }
    val networkResult =
      spotifyService.getSeveralTracks(ids.joinToString(separator = ",")).successOrNothing {
        throw RuntimeException()
      }
    return ApiResult.success(networkResult.tracks)
  }

  override suspend fun getSeveralAlbums(ids: List<String>): ApiResult<List<FullAlbum>, Unit> {
    require(ids.size in 1..20) { "You can only query for up to 20 albums at a time." }
    val networkResult =
      spotifyService.getSeveralAlbums(ids.joinToString(separator = ",")).successOrNothing {
        throw RuntimeException()
      }

    // find any albums that don't return the full results

    return ApiResult.success(networkResult.albums)
  }
}
