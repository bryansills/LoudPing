package ninja.bryansills.loudping.ui.refreshtokenentry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ninja.bryansills.loudping.di.Dispatcher
import ninja.bryansills.loudping.di.LoudPingDispatcher
import ninja.bryansills.loudping.network.auth.AuthManager

@HiltViewModel
class RefreshTokenEntryViewModel @Inject constructor(
    private val authManager: AuthManager,
    @Dispatcher(LoudPingDispatcher.Io) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    var input by mutableStateOf(TextFieldValue(""))

    private val _uiState = MutableStateFlow(RefreshTokenUiState(isDoingWork = true, error = null))
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<RefreshTokenEvent>()
    val events = eventChannel.consumeAsFlow()

    fun submit() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isDoingWork = true) }
            val enteredText = input.text

            if (enteredText.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isDoingWork = false,
                        error = "Gimme something to work with...",
                    )
                }
            } else {
                try {
                    authManager.setRefreshToken(enteredText)
                    eventChannel.send(RefreshTokenEvent.Success)
                } catch (ex: Exception) {
                    _uiState.update {
                        RefreshTokenUiState(
                            isDoingWork = false,
                            error = ex.message ?: "Something weird happened",
                        )
                    }
                }
            }
        }
    }
}

data class RefreshTokenUiState(
    val isDoingWork: Boolean,
    val error: String? = null,
)

sealed interface RefreshTokenEvent {
    data object Success : RefreshTokenEvent
}
