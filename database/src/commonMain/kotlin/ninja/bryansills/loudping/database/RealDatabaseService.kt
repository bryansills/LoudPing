package ninja.bryansills.loudping.database

import androidx.paging.PagingSource
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.paging3.QueryPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import ninja.bryansills.loudping.database.model.Album
import ninja.bryansills.loudping.database.model.TrackPlayContext
import ninja.bryansills.loudping.database.model.TrackPlayRecord

class RealDatabaseService(
    private val database: Database,
    private val timestampFormatter: DateTimeFormat<DateTimeComponents> = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET,
) : DatabaseService {
    override suspend fun insertTrackPlayRecords(records: List<TrackPlayRecord>) {
        val queries = database.trackPlayRecordQueries
        database.transaction {
            records.forEach { record ->
                queries.insert_track(
                    spotifyTrackId = record.trackId,
                    trackNumber = record.trackNumber.toLong(),
                    trackTitle = record.trackTitle,
                    spotifyAlbumId = record.album.spotifyId,
                    timestamp = record.timestamp.format(timestampFormatter),
                    context = record.context,
                )
                queries.insert_album(
                    spotifyId = record.album.spotifyId,
                    trackCount = record.album.trackCount.toLong(),
                    title = record.album.title,
                    coverImage = record.album.coverImage,
                )
                record.artists.forEach { trackArtist ->
                    queries.insert_artist(
                        spotifyId = trackArtist.spotifyId,
                        name = trackArtist.name,
                    )
                }
            }
        }
    }

    override suspend fun insertAlbumPlayRecord(albumId: Long, timestamp: Instant) {
        val queries = database.albumPlayRecordQueries
        queries.insert(albumId, timestamp.toString())
    }

    override suspend fun insertTrackPlayGap(start: Instant, end: Instant) {
        val queries = database.trackPlayGapQueries
        queries.insert(
            start = start.format(timestampFormatter),
            end = end.format(timestampFormatter),
        )
    }

    override suspend fun getAllPlayedTracks(): List<TrackPlayRecord> {
        return database
            .trackPlayRecordQueries
            .select_all(mapper = ::DomainTrackPlayRecord)
            .awaitAsList()
    }

    override val playedTracks: PagingSource<Int, TrackPlayRecord> = QueryPagingSource(
        countQuery = database.trackPlayRecordQueries.count_tracks(),
        transacter = database.trackPlayRecordQueries,
        context = Dispatchers.IO,
        queryProvider = { limit, offset ->
            database.trackPlayRecordQueries.all(
                limit = limit,
                offset = offset,
                mapper = ::DomainTrackPlayRecord,
            )
        },
    )
}

@Suppress("ktlint:standard:function-naming")
private fun DomainTrackPlayRecord(
    id: Long,
    track_id: String,
    track_number: Long,
    track_title: String,
    album_id: String,
    timestamp: String,
    context: TrackPlayContext?,
): TrackPlayRecord {
    return TrackPlayRecord(
        trackId = track_id,
        trackNumber = track_number.toInt(),
        trackTitle = track_title,
        album = Album(
            spotifyId = album_id,
            title = "TODO TITLE",
            trackCount = -1,
            coverImage = null,
        ),
        artists = listOf(),
        timestamp = Instant.parse(timestamp),
        context = context ?: TrackPlayContext.Unknown,
    )
}
