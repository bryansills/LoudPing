package ninja.bryansills.loudping.database

import androidx.paging.PagingSource
import app.cash.sqldelight.async.coroutines.awaitAsList
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import ninja.bryansills.loudping.database.model.Album
import ninja.bryansills.loudping.database.model.Artist
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
                    queries.insert_track_artist(
                        spotifyTrackId = record.trackId,
                        spotifyArtistId = trackArtist.spotifyId,
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

    override val playedTracks: PagingSource<String, TrackPlayRecord>
        get() {
            return TransformableKeyedQueryPagingSource(
                transacter = database.trackPlayRecordQueries,
                context = Dispatchers.IO,
                pageBoundariesProvider = { anchor: String?, limit: Long ->
                    database.trackPlayRecordQueries.keyed_page_boundaries_play_record(limit, anchor)
                },
                queryProvider = { beginInclusive: String, endExclusive: String? ->
                    database.trackPlayRecordQueries.get_keyed_play_records(
                        beginInclusive = beginInclusive,
                        endExclusive = endExclusive,
                    )
                },
                transform = { databaseRows ->
                    databaseRows
                        .groupBy { it.timestamp }
                        .values
                        .mapNotNull { rowsForOnePlay ->
                            if (rowsForOnePlay.isNotEmpty()) {
                                val firstRow = rowsForOnePlay.first()
                                TrackPlayRecord(
                                    trackId = firstRow.spotify_track_id,
                                    trackNumber = -1, // TODO: fill in
                                    trackTitle = firstRow.track_title,
                                    album = Album(
                                        spotifyId = firstRow.spotify_album_id,
                                        title = firstRow.album_title,
                                        trackCount = -1, // TODO: fill in
                                        coverImage = firstRow.cover_image,
                                    ),
                                    artists = rowsForOnePlay.map { rowArtist ->
                                        Artist(
                                            spotifyId = "todo", // TODO: fill in
                                            name = rowArtist.artist_name,
                                        )
                                    },
                                    timestamp = Instant.parse(firstRow.timestamp),
                                    context = TrackPlayContext.Unknown, // TODO: fill in
                                )
                            } else {
                                null
                            }
                        }
                },
            )
        }
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
