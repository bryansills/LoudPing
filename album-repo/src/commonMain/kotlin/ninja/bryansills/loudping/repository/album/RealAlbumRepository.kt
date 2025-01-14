package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.model.Album
import ninja.bryansills.loudping.network.NetworkService
import ninja.bryansills.loudping.network.model.track.TrackAlbum
import ninja.bryansills.loudping.network.model.track.coverImageUrl

class RealAlbumRepository(
    private val network: NetworkService,
    private val database: DatabaseService,
) : AlbumRepository {
    override suspend fun getAlbumByTrackId(trackId: String, shouldQueryNetwork: Boolean): Album? {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbumsByTrackIds(trackIds: List<String>): Map<String, Album> {
        val networkTracks = network.getSeveralTracks(trackIds)

        networkTracks.forEach { track ->
            val databaseTrack = track.album.toDatabase()
        }

        TODO()
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
