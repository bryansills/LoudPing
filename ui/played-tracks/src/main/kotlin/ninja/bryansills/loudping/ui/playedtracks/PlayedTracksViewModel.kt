package ninja.bryansills.loudping.ui.playedtracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.model.TrackPlayRecord

@HiltViewModel
class PlayedTracksViewModel @Inject constructor(
    private val databaseService: DatabaseService,
) : ViewModel() {
    val tracks = flow<List<TrackPlayRecord>> {
        databaseService.getAllPlayedTracks()
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000L),
            initialValue = listOf(),
        )
}
