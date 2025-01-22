package ninja.bryansills.loudping.repository.track

import kotlin.time.Duration.Companion.milliseconds
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.model.Album
import ninja.bryansills.loudping.database.model.Artist
import ninja.bryansills.loudping.database.model.Track
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.getTrack
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist
import ninja.bryansills.loudping.network.model.track.Track as NetworkTrack
import ninja.bryansills.loudping.network.model.track.TrackAlbum
import ninja.bryansills.loudping.network.model.track.coverImageUrl

class RealTrackRepository(
    private val network: NetworkService,
    private val database: DatabaseService,
) : TrackRepository {
    override suspend fun getTrackBySpotifyId(trackId: String, shouldQueryNetwork: Boolean): Track? {
        val cachedDatabaseValue = database.getTrackFromSpotifyId(trackId)
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

    override suspend fun getTracksBySpotifyIds(trackIds: List<String>): List<Track> {
        val networkTracks = network.getSeveralTracks(trackIds)
        return networkTracks.map { it.toDatabase() }
    }
}

private fun NetworkTrack.toDatabase(): Track {
    return Track(
        spotifyId = this.id,
        title = this.name,
        trackNumber = this.track_number,
        discNumber = this.disc_number,
        duration = this.duration_ms.milliseconds,
        album = this.album.toDatabase(),
        artists = this.artists.map { it.toDatabase() },
    )
}

private fun TrackAlbum.toDatabase(): Album {
    return Album(
        spotifyId = this.id,
        title = this.name,
        trackCount = this.total_tracks,
        coverImage = this.coverImageUrl,
    )
}

private fun SimplifiedArtist.toDatabase(): Artist {
    return Artist(
        spotifyId = this.id,
        name = this.name,
    )
}
