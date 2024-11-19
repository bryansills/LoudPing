package ninja.bryansills.loudping.ui.playedtracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ninja.bryansills.loudping.database.DatabaseService

@HiltViewModel
class PlayedTracksViewModel @Inject constructor(
    private val databaseService: DatabaseService,
) : ViewModel() {
    val tracks = flow { emit(databaseService.getAllPlayedTracks()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000L),
            initialValue = listOf(),
        )

    val coolTracks = Pager(
        config = PagingConfig(pageSize = 50),
    ) { databaseService.playedTracks }
        .flow
        .cachedIn(viewModelScope)
}
