package ninja.bryansills.loudping.grouper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ninja.bryansills.loudping.core.model.TrackPlayRecord

class DefaultAlbumGrouper(
    private val previousTrackCount: Int = 5,
    private val followingTrackCount: Int = 5,
) : AlbumGrouper {
    override fun invoke(songs: Flow<List<TrackPlayRecord>>) = flow {
        val workingSpace = WorkingSpace(previousTrackCount = previousTrackCount)

        songs.collect { newChunk ->
            newChunk.fold(workingSpace) { acc, trackPlayRecord ->
                if (trackPlayRecord.shouldAddToCurrent(acc.currentAlbum)) {
                    acc.currentAlbum.add(trackPlayRecord)
                } else {
                    acc.stillProcessing.add(trackPlayRecord)
                }

                val albumGroups = acc.squeezeOutGroups(minStillProcessingSize = followingTrackCount)
                albumGroups.forEach { emit(it) }

                acc
            }
        }

        val finalAlbumGroups = workingSpace.squeezeOutGroups(minStillProcessingSize = 0)
        finalAlbumGroups.forEachIndexed { index, albumGroup ->
            // we don't emit the last album since it could be incomplete
            if (index != finalAlbumGroups.lastIndex) {
                emit(albumGroup)
            }
        }
    }
}

private fun TrackPlayRecord.shouldAddToCurrent(currentAlbum: List<TrackPlayRecord>): Boolean {
    val isSameAlbum = this.track.album.spotifyId == currentAlbum.lastOrNull()?.track?.album?.spotifyId
    return isSameAlbum || currentAlbum.isEmpty()
}

/**
 * Work through the stillProcessing backlog to find all the possible albums.
 *
 * @param minStillProcessingSize Keep squeezing until stillProcessing is smaller
 */
internal fun WorkingSpace.squeezeOutGroups(minStillProcessingSize: Int) = buildList {
    while (currentAlbum.isNotEmpty() && stillProcessing.size >= minStillProcessingSize) {
        val albumGroup = AlbumGroup(
            previousSongs = tracksPrevious.values.toList(),
            supposedAlbum = currentAlbum.toList(),
            subsequentSongs = stillProcessing.subList(0, minStillProcessingSize).toList(),
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
