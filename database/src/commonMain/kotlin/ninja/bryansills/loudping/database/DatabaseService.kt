package ninja.bryansills.loudping.database

import androidx.paging.PagingSource
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.database.model.Album
import ninja.bryansills.loudping.database.model.Track
import ninja.bryansills.loudping.database.model.TrackPlayRecord

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

  /** @param trackId Just the base62 data. Do not include the URI prefix "spotify:track:". */
  suspend fun getAlbumFromTrackId(trackId: String): Album?

  suspend fun insertAlbum(album: Album, associatedTrackIds: List<String>)

  /** @param trackId Just the base62 data. Do not include the URI prefix "spotify:track:". */
  suspend fun getTrackFromSpotifyId(trackId: String): Track?

  /** @param trackIds Just the base62 data. Do not include the URI prefix "spotify:track:". */
  suspend fun getTracksFromSpotifyIds(trackIds: List<String>): List<Track>

  suspend fun insertTrack(track: Track)
}
