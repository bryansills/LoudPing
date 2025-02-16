package ninja.bryansills.loudping.repository.track

import ninja.bryansills.loudping.database.model.Track

interface TrackRepository {
    suspend fun getTrackBySpotifyId(trackId: String, shouldQueryNetwork: Boolean = false): Track?

    suspend fun getTracksBySpotifyIds(trackIds: List<String>, shouldQueryNetworkForMissing: Boolean): MultiTrackResult
}

sealed interface MultiTrackResult {
    data class Success(val tracks: List<Track>) : MultiTrackResult

    /**
     * Spotify lost some data around certain tracks, so we don't always get a full response back
     * @param tracks the tracks they have info for
     * @param missingIds the ids of tracks they no longer have info for
     */
    data class Mixed(val tracks: List<Track>, val missingIds: List<String>) : MultiTrackResult

    data class Failure(val exception: Exception) : MultiTrackResult
}
