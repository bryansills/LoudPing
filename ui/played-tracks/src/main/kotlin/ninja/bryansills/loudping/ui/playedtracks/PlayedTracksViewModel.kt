package ninja.bryansills.loudping.ui.playedtracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import ninja.bryansills.loudping.database.DatabaseService

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class PlayedTracksViewModel(private val databaseService: DatabaseService) : ViewModel() {
  val coolTracks =
    Pager(config = PagingConfig(pageSize = 50)) { databaseService.playedTracks }
      .flow
      .cachedIn(viewModelScope)
}
