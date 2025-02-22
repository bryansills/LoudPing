package ninja.bryansills.loudping.repository.album

import ninja.bryansills.loudping.core.model.Album
import ninja.bryansills.loudping.database.DatabaseService
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

    override suspend fun getAlbumsByTrackIds(trackIds: List<String>): List<Album> {
        val cachedAlbums = trackIds
            .mapNotNull { trackId ->
                val cachedDatabaseValue = database.getAlbumFromTrackId(trackId)
                cachedDatabaseValue?.let { trackId to it }
            }
            .toMap()
        val stillNeedDataTrackIds = trackIds.filter { !cachedAlbums.keys.contains(it) }
        val networkTracks = if (stillNeedDataTrackIds.isNotEmpty()) {
            network.getSeveralTracks(stillNeedDataTrackIds)
        } else {
            listOf()
        }

        val freshAlbums = networkTracks.associate { networkTrack ->
            val databaseAlbum = networkTrack.album.toDatabase()
            networkTrack.id to databaseAlbum
        }

        freshAlbums.entries.forEach { (trackId, databaseAlbum) ->
            database.insertAlbum(album = databaseAlbum, associatedTrackIds = listOf(trackId))
        }

        return trackIds.map { ogTrackId ->
            val trackResult = freshAlbums[ogTrackId] ?: cachedAlbums[ogTrackId]
            checkNotNull(trackResult) { "The album for ID $ogTrackId is missing???" }
            trackResult
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
