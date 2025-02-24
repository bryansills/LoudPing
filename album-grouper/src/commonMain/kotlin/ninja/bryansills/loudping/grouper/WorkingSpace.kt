package ninja.bryansills.loudping.grouper

import ninja.bryansills.loudping.core.model.TrackPlayRecord

internal data class WorkingSpace(
    private val previousTrackCount: Int = 5,
    val tracksPrevious: MaxSizeStack<TrackPlayRecord> = MaxSizeStack(maxSize = previousTrackCount),
    val currentAlbum: MutableList<TrackPlayRecord> = mutableListOf(),
    val stillProcessing: MutableList<TrackPlayRecord> = mutableListOf(),
)
