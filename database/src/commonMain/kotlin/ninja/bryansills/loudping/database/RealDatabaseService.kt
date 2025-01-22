package ninja.bryansills.loudping.database

import androidx.paging.PagingSource
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import ninja.bryansills.loudping.database.model.Album
import ninja.bryansills.loudping.database.model.Artist
import ninja.bryansills.loudping.database.model.Track
import ninja.bryansills.loudping.database.model.Track.Companion.MISSING_DISC_NUMBER
import ninja.bryansills.loudping.database.model.Track.Companion.MISSING_DURATION
import ninja.bryansills.loudping.database.model.TrackPlayContext
import ninja.bryansills.loudping.database.model.TrackPlayRecord

class RealDatabaseService(
    private val database: Database,
    private val timestampFormatter: DateTimeFormat<DateTimeComponents> = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET,
) : DatabaseService {
    override suspend fun insertTrackPlayRecords(records: List<TrackPlayRecord>) {
        database.transaction {
            records.forEach { record ->
                database.trackPlayRecordQueries.insert_track(
                    spotifyTrackId = record.trackId,
                    trackNumber = record.trackNumber.toLong(),
                    trackTitle = record.trackTitle,
                    spotifyAlbumId = record.album.spotifyId,
                    timestamp = record.timestamp.format(timestampFormatter),
                    context = record.context,
                )

                insertTrackInternal(
                    track = Track(
                        spotifyId = record.trackId,
                        title = record.trackTitle,
                        trackNumber = record.trackNumber,
                        discNumber = MISSING_DISC_NUMBER,
                        duration = MISSING_DURATION,
                        album = record.album,
                        artists = record.artists,
                    ),
                )
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

    override suspend fun getAlbumFromTrackId(trackId: String): Album? {
        return try {
            val result = database.albumQueries.get_album_from_track_id(trackId).awaitAsOne()
            Album(
                spotifyId = result.spotify_id,
                title = result.title,
                trackCount = result.track_count.toInt(),
                coverImage = result.cover_image,
            )
        } catch (ex: Exception) {
            println(ex) // TODO: better logging
            null
        }
    }

    override suspend fun insertAlbum(album: Album, associatedTrackIds: List<String>) {
        database.transaction {
            insertAlbumInternal(album = album, associatedTrackIds = associatedTrackIds)
        }
    }

    private suspend fun insertAlbumInternal(album: Album, associatedTrackIds: List<String>) {
        database.trackPlayRecordQueries.insert_album(
            spotifyId = album.spotifyId,
            trackCount = album.trackCount.toLong(),
            title = album.title,
            coverImage = album.coverImage,
        )
        associatedTrackIds.forEach { trackId ->
            database.albumQueries.insert_artist_album(
                spotifyTrackId = trackId,
                spotifyAlbumId = album.spotifyId,
            )
        }
    }

    override suspend fun getTrackFromSpotifyId(trackId: String): Track? {
        return try {
            val tracks = database.trackQueries.get_track_from_spotify_id(spotifyTrackId = trackId).awaitAsList()
            tracks
                .groupBy { it.spotify_track_id }
                .values
                .map { rowsForTrack ->
                    val firstRow = rowsForTrack.first()
                    Track(
                        spotifyId = firstRow.spotify_track_id,
                        title = firstRow.title,
                        trackNumber = firstRow.track_number.toInt(),
                        discNumber = firstRow.disc_number.toInt(),
                        duration = firstRow.duration_ms.milliseconds,
                        album = Album(
                            spotifyId = firstRow.spotify_album_id,
                            title = firstRow.album_title,
                            trackCount = firstRow.album_track_count.toInt(),
                            coverImage = firstRow.album_cover_image,
                        ),
                        artists = rowsForTrack.map { trackArtist ->
                            Artist(
                                spotifyId = trackArtist.spotify_artist_id,
                                name = trackArtist.artist_name,
                            )
                        },
                    )
                }
                .firstOrNull()
        } catch (ex: Exception) {
            println(ex) // TODO: better logging
            null
        }
    }

    override suspend fun insertTrack(track: Track) {
        database.transaction {
            insertTrackInternal(track = track)
        }
    }

    private suspend fun insertTrackInternal(track: Track) {
        database.trackQueries.insert_track(
            spotifyTrackId = track.spotifyId,
            title = track.title,
            trackNumber = track.trackNumber.toLong(),
            discNumber = track.discNumber.toLong(),
            durationMs = track.duration.inWholeMilliseconds,
        )
        insertAlbumInternal(
            album = track.album,
            associatedTrackIds = listOf(track.spotifyId),
        )
        track.artists.forEach { trackArtist ->
            insertArtistInternal(
                artist = trackArtist,
                associatedTrackIds = listOf(track.spotifyId),
            )
        }
    }

    private suspend fun insertArtistInternal(artist: Artist, associatedTrackIds: List<String>) {
        database.trackPlayRecordQueries.insert_artist(
            spotifyId = artist.spotifyId,
            name = artist.name,
        )
        associatedTrackIds.forEach { trackId ->
            database.trackPlayRecordQueries.insert_track_artist(
                spotifyTrackId = trackId,
                spotifyArtistId = artist.spotifyId,
            )
        }
    }
}
