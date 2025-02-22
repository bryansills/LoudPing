package ninja.bryansills.loudping.grouper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ninja.bryansills.loudping.core.model.TrackPlayRecord

class DefaultAlbumGrouper : AlbumGrouper {
    override fun invoke(songs: Flow<List<TrackPlayRecord>>) = flow {
        val workingSpace = WorkingSpace()

        songs.collect { newChunk ->
            newChunk.fold(workingSpace) { acc, trackPlayRecord ->
                if (trackPlayRecord.track.album.spotifyId != acc.currentAlbum.lastOrNull()?.track?.album?.spotifyId) {
                    acc.stillProcessing.add(trackPlayRecord)
                } else {
                    acc.currentAlbum.add(trackPlayRecord)
                }

                val albumGroups = acc.squeezeOutGroups(minStillProcessingSize = 5)
                albumGroups.forEach { emit(it) }

                acc
            }
        }

        val finalAlbumGroups = workingSpace.squeezeOutGroups(minStillProcessingSize = 0)
        finalAlbumGroups.forEach { emit(it) }
    }
}

/**
 * Work through the stillProcessing backlog to find all the possible albums.
 *
 * @param minStillProcessingSize Keep squeezing until stillProcessing is smaller
 */
internal fun WorkingSpace.squeezeOutGroups(minStillProcessingSize: Int) = buildList {
    while (currentAlbum.isNotEmpty() && stillProcessing.size >= minStillProcessingSize) {
        val albumGroup = AlbumGroup(
            previousSongs = tracksPrevious.values,
            supposedAlbum = currentAlbum,
            subsequentSongs = stillProcessing.subList(0, minStillProcessingSize),
        )

        add(albumGroup)

        val albumCarryoverStart = maxOf(0, currentAlbum.size - minStillProcessingSize)
        val albumCarryover = currentAlbum.subList(albumCarryoverStart, currentAlbum.size)

        albumCarryover.forEach { tracksPrevious.push(it) }
        currentAlbum.clear()

        if (stillProcessing.isNotEmpty()) {
            val firstOfNext = stillProcessing.removeFirst()
            val nextAlbum = mutableListOf(firstOfNext)

            while (firstOfNext.track.album.spotifyId == stillProcessing.firstOrNull()?.track?.album?.spotifyId) {
                val albumTrack = stillProcessing.removeFirst()
                nextAlbum.add(albumTrack)
            }

            currentAlbum.addAll(nextAlbum)
        }
    }
}
