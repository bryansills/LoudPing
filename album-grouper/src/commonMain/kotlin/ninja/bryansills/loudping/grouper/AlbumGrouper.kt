package ninja.bryansills.loudping.grouper

import kotlinx.coroutines.flow.Flow
import ninja.bryansills.loudping.database.model.TrackPlayRecord

interface AlbumGrouper {
    operator fun invoke(songs: Flow<List<TrackPlayRecord>>): Flow<AlbumGroup>
}
