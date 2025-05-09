@file:Suppress("ktlint:standard:discouraged-comment-location")

package ninja.bryansills.loudping.database

import androidx.paging.PagingSource
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import ninja.bryansills.loudping.core.model.AlbumType
import ninja.bryansills.loudping.core.model.Artist
import ninja.bryansills.loudping.core.model.FullAlbum
import ninja.bryansills.loudping.core.model.Track
import ninja.bryansills.loudping.core.model.TrackAlbum
import ninja.bryansills.loudping.core.model.TrackPlayContext
import ninja.bryansills.loudping.core.model.TrackPlayRecord

class RealDatabaseService(
    private val database: Database,
    private val timestampFormatter: DateTimeFormat<DateTimeComponents> = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET,
) : DatabaseService {
    override suspend fun insertTrackPlayRecords(records: List<TrackPlayRecord>) {
        database.transaction {
            records.forEach { record ->
                database.trackPlayRecordQueries.insert_track(
                    spotifyTrackId = record.track.spotifyId,
                    trackNumber = record.track.trackNumber.toLong(),
                    trackTitle = record.track.title,
                    spotifyAlbumId = record.track.album.spotifyId,
                    timestamp = record.timestamp.format(timestampFormatter),
                    context = record.context,
                )
                insertTrackInternal(track = record.track)
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
                    database.trackPlayRecordQueries.get_desc_page_boundaries(limit, anchor)
                },
                queryProvider = { beginInclusive: String, endExclusive: String? ->
                    database.trackPlayRecordQueries.get_desc_track_play_record_page(
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
                                    track = Track(
                                        spotifyId = firstRow.spotify_track_id,
                                        title = firstRow.track_title,
                                        trackNumber = -1, // TODO: fill in
                                        discNumber = -1, // TODO: fill in
                                        duration = ZERO, // TODO: fill in
                                        album = TrackAlbum(
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
                                    ),
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

    override suspend fun getAlbumFromTrackId(trackId: String): TrackAlbum? {
        return try {
            val result = database.trackAlbumQueries.get_album_from_track_id(trackId).awaitAsOne()
            TrackAlbum(
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

    override suspend fun insertAlbum(album: TrackAlbum, associatedTrackIds: List<String>) {
        database.transaction {
            insertAlbumInternal(album = album, associatedTrackIds = associatedTrackIds)
        }
    }

    private suspend fun insertAlbumInternal(album: TrackAlbum, associatedTrackIds: List<String>) {
        database.albumQueries.insert_album(
            spotifyId = album.spotifyId,
            trackCount = album.trackCount.toLong(),
            title = album.title,
            coverImage = album.coverImage,
            type = AlbumType.Unknown,
        )
        associatedTrackIds.forEach { trackId ->
            database.trackAlbumQueries.insert_artist_album(
                spotifyTrackId = trackId,
                spotifyAlbumId = album.spotifyId,
            )
        }
    }

    override suspend fun getTrackForSpotifyId(trackId: String): Track? {
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
                        album = TrackAlbum(
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

    override suspend fun getTracksForSpotifyIds(trackIds: List<String>): List<Track> {
        val tracks = database
            .trackQueries
            .get_tracks_from_spotify_ids(spotifyTrackIds = trackIds)
            .awaitAsList()

        return tracks
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
                    album = TrackAlbum(
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
    }

    override suspend fun getAlbumForSpotifyId(albumId: String): FullAlbum? {
        return try {
            val databaseResults = database.albumQueries.get_album_from_spotify_id(spotifyAlbumId = albumId).awaitAsList()
            val firstRow = databaseResults.first()
            FullAlbum(
                spotifyId = firstRow.spotify_album_id,
                title = firstRow.title,
                trackCount = firstRow.track_count.toInt(),
                coverImage = firstRow.cover_image,
                type = firstRow.album_type ?: AlbumType.Unknown,
                artists = TODO(),
                tracks = TODO(),
//                totalDuration = databaseResults.sumOf { it.track_duration }.milliseconds,
            )
        } catch (ex: Exception) {
            println(ex.message)
            null
        }
    }

    override suspend fun getAlbumsForSpotifyIds(albumIds: List<String>): List<FullAlbum> {
        val databaseResults = database.albumQueries.get_albums_from_spotify_ids(albumIds).awaitAsList()

        return databaseResults
            .groupBy { it.spotify_album_id }
            .values
            .map { albumRows ->
                val firstRow = albumRows.first()
                FullAlbum(
                    spotifyId = firstRow.spotify_album_id,
                    title = firstRow.title,
                    trackCount = firstRow.track_count.toInt(),
                    coverImage = firstRow.cover_image,
                    type = firstRow.album_type ?: AlbumType.Unknown,
//                    totalDuration = albumRows.sumOf { it.track_duration }.milliseconds,
                    artists = TODO(),
                    tracks = TODO(),
                )
            }
    }

    override suspend fun insertTrack(track: Track) {
        database.transaction {
            insertTrackInternal(track = track)
        }
    }

    override fun getTracksBetween(newerTime: Instant, olderTime: Instant) = flow {
        val pageTimestamps = database
            .trackPlayRecordQueries
            .get_asc_constrained_page_boundaries(
                pageSize = 100L,
                olderTime = olderTime.format(timestampFormatter),
                newerTime = newerTime.format(timestampFormatter),
            )
            .awaitAsList()

        val pageInstants = pageTimestamps.map { Instant.parse(it) }

        (listOf(olderTime) + pageInstants + newerTime)
            .windowed(2)
            .forEach { (older, newer) ->
                val pageResults = database
                    .trackPlayRecordQueries
                    .get_asc_track_play_record_page(
                        olderTime = older.format(timestampFormatter),
                        newerTime = newer.format(timestampFormatter),
                    )
                    .awaitAsList()

                val trackPlayRecords = pageResults
                    .groupBy { it.timestamp }
                    .values
                    .map { rowsForRecord ->
                        val firstRow = rowsForRecord.first()
                        TrackPlayRecord(
                            track = Track(
                                spotifyId = firstRow.spotify_track_id,
                                title = firstRow.track_title,
                                trackNumber = firstRow.track_number.toInt(),
                                discNumber = firstRow.disc_number.toInt(),
                                duration = firstRow.duration_ms.milliseconds,
                                album = TrackAlbum(
                                    spotifyId = firstRow.spotify_album_id,
                                    title = firstRow.album_title,
                                    trackCount = firstRow.album_track_count.toInt(),
                                    coverImage = firstRow.album_cover_image,
                                ),
                                artists = rowsForRecord.map { dbRow ->
                                    Artist(
                                        spotifyId = dbRow.spotify_artist_id,
                                        name = dbRow.artist_name,
                                    )
                                },
                            ),
                            timestamp = Instant.parse(firstRow.timestamp),
                            context = firstRow.context ?: TrackPlayContext.Unknown,
                        )
                    }

                emit(trackPlayRecords)
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
        database.artistQueries.insert_artist(
            spotifyId = artist.spotifyId,
            name = artist.name,
        )
        associatedTrackIds.forEach { trackId ->
            database.trackArtistQueries.insert_track_artist(
                spotifyTrackId = trackId,
                spotifyArtistId = artist.spotifyId,
            )
        }
    }
}
