package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.model.Album
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.getTrack
import ninja.bryansills.loudping.network.model.track.TrackAlbum
import ninja.bryansills.loudping.network.model.track.coverImageUrl

class RealAlbumRepository(
    private val network: NetworkService,
    private val database: DatabaseService,
) : AlbumRepository {
    override suspend fun getAlbumByTrackId(trackId: String, shouldQueryNetwork: Boolean): Album? {
        val cachedDatabaseValue = database.getAlbumFromTrackId(trackId)
        return cachedDatabaseValue
            ?: if (shouldQueryNetwork) {
                val networkTrack = network.getTrack(trackId)
                val databaseAlbum = networkTrack.album.toDatabase()
                database.insertAlbum(album = databaseAlbum, associatedTrackIds = listOf(trackId))
                databaseAlbum
            } else {
                null
            }
    }

    override suspend fun getAlbumsByTrackIds(trackIds: List<String>): Map<String, Album> {
        val networkTracks = network.getSeveralTracks(trackIds)

        return networkTracks.associate { track ->
            val databaseAlbum = track.album.toDatabase()
            database.insertAlbum(album = databaseAlbum, associatedTrackIds = listOf(track.id))
            track.id to databaseAlbum
        }
    }
}

private fun TrackAlbum.toDatabase(): Album {
    return Album(
        spotifyId = this.id,
        title = this.name,
        trackCount = this.total_tracks,
        coverImage = this.coverImageUrl,
    )
}
