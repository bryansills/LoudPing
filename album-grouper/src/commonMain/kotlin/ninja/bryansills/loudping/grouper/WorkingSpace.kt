package ninja.bryansills.loudping.grouper

import ninja.bryansills.loudping.core.model.TrackPlayRecord

internal data class WorkingSpace(
    val tracksPrevious: MaxSizeStack<TrackPlayRecord> = MaxSizeStack(maxSize = 5),
    val currentAlbum: MutableList<TrackPlayRecord> = mutableListOf(),
    val stillProcessing: MutableList<TrackPlayRecord> = mutableListOf(),
)
