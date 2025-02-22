package ninja.bryansills.loudping.grouper

import ninja.bryansills.loudping.core.model.TrackPlayRecord

data class AlbumGroup(
    val previousSongs: List<TrackPlayRecord>,
    val supposedAlbum: List<TrackPlayRecord>,
    val subsequentSongs: List<TrackPlayRecord>,
)
