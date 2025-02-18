package ninja.bryansills.loudping.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ninja.bryansills.loudping.session.Session
import ninja.bryansills.loudping.session.SessionManager

@HiltViewModel
class HomeViewModel @Inject constructor(private val sessionManager: SessionManager) : ViewModel() {
    val uiState =
        sessionManager.currentSession
            .map { session ->
                when (session) {
                    is Session.LoggedIn -> HomeUiState.LoggedIn
                    Session.LoggedOut -> HomeUiState.LoggedOut
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = HomeUiState.Loading,
            )
}

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data object LoggedOut : HomeUiState

    data object LoggedIn : HomeUiState
}
