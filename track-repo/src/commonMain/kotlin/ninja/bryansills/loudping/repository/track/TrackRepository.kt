package ninja.bryansills.loudping.repository.track

import ninja.bryansills.loudping.database.model.Track

interface TrackRepository {
    suspend fun getTrackBySpotifyId(trackId: String, shouldQueryNetwork: Boolean = false): Track?

    /**
     * ALWAYS makes a network request, so try the other method first.
     */
    suspend fun getTracksBySpotifyIds(trackIds: List<String>): List<Track>
}
