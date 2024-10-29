package ninja.bryansills.loudping.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ninja.bryansills.loudping.foreman.Foreman

@HiltViewModel
class HomeLoggedInViewModel @Inject constructor(private val foreman: Foreman) : ViewModel() {
}
