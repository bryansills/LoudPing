package ninja.bryansills.loudping.network

import com.slack.eithernet.ApiResult
import com.slack.eithernet.successOrNothing
import ninja.bryansills.loudping.network.model.PrivateUserResponse
import ninja.bryansills.loudping.network.model.RecentlyPlayedResponse
import ninja.bryansills.loudping.network.model.SavedAlbumsResponse
import ninja.bryansills.loudping.network.model.album.FullAlbum
import ninja.bryansills.loudping.network.model.track.Track

interface NetworkService {
    suspend fun getMe(): ApiResult<PrivateUserResponse, Unit>
    suspend fun getRecentlyPlayed(): ApiResult<RecentlyPlayedResponse, Unit>
    suspend fun getSavedAlbums(): ApiResult<SavedAlbumsResponse, Unit>

    suspend fun getSeveralTracks(ids: List<String>): ApiResult<List<Track>, Unit>

    suspend fun getSeveralAlbums(ids: List<String>): ApiResult<List<FullAlbum>, Unit>
}

suspend fun NetworkService.getTrack(id: String): Track {
    return this.getSeveralTracks(listOf(id)).successOrNothing { throw RuntimeException() }.first()
}
