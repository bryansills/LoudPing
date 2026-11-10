package ninja.bryansills.loudping.ui.home

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import ninja.bryansills.loudping.foreman.Foreman

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class HomeLoggedInViewModel(private val foreman: Foreman) : ViewModel()
