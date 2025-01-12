package ninja.bryansills.loudping.deephistory

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DeepHistoryRecord(
    val ts: Instant,
    val ms_played: Long,
    val master_metadata_track_name: String?,
    val master_metadata_album_artist_name: String?,
    val master_metadata_album_album_name: String?,
    val spotify_track_uri: String?,
    val reason_start: String,
    val reason_end: String,
    val shuffle: Boolean,
    val skipped: Boolean,
)
