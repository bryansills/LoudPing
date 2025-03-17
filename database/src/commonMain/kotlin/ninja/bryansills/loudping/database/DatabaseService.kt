package ninja.bryansills.loudping.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.core.model.FullAlbum
import ninja.bryansills.loudping.core.model.Track
import ninja.bryansills.loudping.core.model.TrackAlbum
import ninja.bryansills.loudping.core.model.TrackPlayRecord

interface DatabaseService {
    suspend fun insertTrackPlayRecords(records: List<TrackPlayRecord>)

    suspend fun insertAlbumPlayRecord(
        albumId: Long,
        timestamp: Instant,
    )

    suspend fun insertTrackPlayGap(
        start: Instant,
        end: Instant,
    )

    val playedTracks: PagingSource<String, TrackPlayRecord>

    /**
     * @param trackId Just the base62 data. Do not include the URI prefix "spotify:track:".
     */
    suspend fun getAlbumFromTrackId(trackId: String): TrackAlbum?

    suspend fun insertAlbum(album: TrackAlbum, associatedTrackIds: List<String>)

    /**
     * @param trackId Just the base62 data. Do not include the URI prefix "spotify:track:".
     */
    suspend fun getTrackForSpotifyId(trackId: String): Track?

    /**
     * @param trackIds Just the base62 data. Do not include the URI prefix "spotify:track:".
     */
    suspend fun getTracksForSpotifyIds(trackIds: List<String>): List<Track>

    suspend fun getAlbumForSpotifyId(albumId: String): FullAlbum?

    suspend fun getAlbumsForSpotifyIds(albumIds: List<String>): List<FullAlbum>

    suspend fun insertTrack(track: Track)

    /**
     * @param newerTime What time to start (often the current time).
     * @param olderTime What (earlier) time to stop.
     * @return Pages of data, sorted by timestamp with the oldest data first. Tracks are missing any
     * info on artists.
     */
    fun getTracksBetween(newerTime: Instant, olderTime: Instant): Flow<List<TrackPlayRecord>>
}
