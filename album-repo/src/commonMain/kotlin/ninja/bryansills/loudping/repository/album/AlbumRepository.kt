package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.core.model.FullAlbum
import ninja.bryansills.loudping.core.model.TrackAlbum

interface AlbumRepository {
    suspend fun getAlbumByTrackId(trackId: String, shouldQueryNetwork: Boolean = false): TrackAlbum?

    /**
     * TODO: migrate this to support partial success
     */
    suspend fun getAlbumsByTrackIds(trackIds: List<String>): List<TrackAlbum>

    suspend fun getAlbumBySpotifyId(albumId: String, shouldQueryNetwork: Boolean = false): FullAlbum?

    suspend fun getAlbumsBySpotifyIds(albumIds: List<String>, shouldQueryNetworkForMissing: Boolean): MultiAlbumResult
}

sealed interface MultiAlbumResult {
    data class Success(val albums: List<FullAlbum>) : MultiAlbumResult

    /**
     * Spotify lost some data around certain albums, so we don't always get a full response back
     * @param albums the albums they have info for
     * @param missingIds the ids of albums they no longer have info for
     */
    data class Mixed(val albums: List<FullAlbum>, val missingIds: List<String>) : MultiAlbumResult

    data class Failure(val exception: Exception) : MultiAlbumResult
}
