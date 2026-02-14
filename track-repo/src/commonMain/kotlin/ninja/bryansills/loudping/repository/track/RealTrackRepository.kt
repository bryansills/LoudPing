package ninja.bryansills.loudping.repository.track

import com.slack.eithernet.successOrNothing
import kotlin.time.Duration.Companion.milliseconds
import ninja.bryansills.loudping.core.model.Artist
import ninja.bryansills.loudping.core.model.Track
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.getTrack
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist
import ninja.bryansills.loudping.network.model.track.Track as NetworkTrack
import ninja.bryansills.loudping.network.model.track.TrackAlbum as NetworkTrackAlbum
import ninja.bryansills.loudping.network.model.track.coverImageUrl

class RealTrackRepository(
    private val network: NetworkService,
    private val database: DatabaseService,
) : TrackRepository {
    override suspend fun getTrackBySpotifyId(trackId: String, shouldQueryNetwork: Boolean): Track? {
        val cachedDatabaseValue = database.getTrackForSpotifyId(trackId)
        return cachedDatabaseValue
            ?: if (shouldQueryNetwork) {
                val networkTrack = network.getTrack(trackId)
                val databaseTrack = networkTrack.toDatabase()
                database.insertTrack(databaseTrack)
                databaseTrack
            } else {
                null
            }
    }

    override suspend fun getTracksBySpotifyIds(
        trackIds: List<String>,
        shouldQueryNetworkForMissing: Boolean,
    ): MultiTrackResult = try {
        val cachedList = database.getTracksForSpotifyIds(trackIds)
        val cachedTracks = trackIds.associateWith { id -> cachedList.find { it.spotifyId == id } }
        val missingAnyTracks = cachedTracks.any { (_, track) -> track == null }

        if (!shouldQueryNetworkForMissing || !missingAnyTracks) {
            cachedTracks.toMultiTrackResult()
        } else {
            val missingCachedIds = cachedTracks.mapNotNull { (id, track) -> id.takeIf { track == null } }
            val networkTracks = network.getSeveralTracks(missingCachedIds).successOrNothing { throw RuntimeException() }
            val freshTracks = networkTracks.associate { networkTrack ->
                val databaseTrack = networkTrack.toDatabase()
                networkTrack.id to databaseTrack
            }
            freshTracks.values.forEach { databaseTrack -> database.insertTrack(databaseTrack) }
            val finalMapping = trackIds.associateWith { ogTrackId ->
                freshTracks[ogTrackId] ?: cachedTracks[ogTrackId]
            }
            finalMapping.toMultiTrackResult()
        }
    } catch (ex: Exception) {
        MultiTrackResult.Failure(ex)
    }
}

private fun NetworkTrack.toDatabase(): Track = Track(
    spotifyId = this.id,
    title = this.name,
    trackNumber = this.track_number,
    discNumber = this.disc_number,
    duration = this.duration_ms.milliseconds,
    album = this.album.toDatabase(),
    artists = this.artists.map { it.toDatabase() },
)

private fun NetworkTrackAlbum.toDatabase(): ninja.bryansills.loudping.core.model.TrackAlbum = ninja.bryansills.loudping.core.model.TrackAlbum(
    spotifyId = this.id,
    title = this.name,
    trackCount = this.total_tracks,
    coverImage = this.coverImageUrl,
)

private fun SimplifiedArtist.toDatabase(): Artist = Artist(
    spotifyId = this.id,
    name = this.name,
)

private fun Map<String, Track?>.toMultiTrackResult(): MultiTrackResult {
    val foundTracks = this.mapNotNull { (_, track) -> track }
    val missingIds = this.mapNotNull { (id, track) -> id.takeIf { track == null } }

    return if (missingIds.isEmpty()) {
        MultiTrackResult.Success(tracks = foundTracks)
    } else {
        MultiTrackResult.Mixed(
            tracks = foundTracks,
            missingIds = missingIds,
        )
    }
}
