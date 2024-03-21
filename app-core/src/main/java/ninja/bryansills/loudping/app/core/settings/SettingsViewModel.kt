package ninja.bryansills.loudping.app.core.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.network.auth.AuthManager
import ninja.bryansills.loudping.network.auth.RawAuthValues

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authManager: AuthManager,
): ViewModel() {
    val uiState = authManager
        .rawValues
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = RawAuthValues(
                accessToken = "",
                accessTokenExpiresAt = Instant.DISTANT_PAST,
                refreshToken = "",
            ),
        )
}
