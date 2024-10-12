package ninja.bryansills.loudping.ui.login

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import ninja.bryansills.loudping.network.auth.AuthManager
import ninja.bryansills.loudping.time.TimeProvider

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authManager: AuthManager,
    private val timeProvider: TimeProvider,
) : ViewModel() {
    private val startTime: Instant = timeProvider.now

    var progress: LoginProgress
        get() = savedStateHandle["wow-overkill"] ?: LoginProgress.Initializing
        set(value) {
            savedStateHandle["wow-overkill"] = value
        }

    val loginUrl: String = authManager.getAuthorizeUrl(startTime)

    private var authJob: Job? = null

    fun setAuthorizationCode(
        code: String,
        state: String,
    ) {
        if (authJob == null && progress == LoginProgress.LoggingIn) {
            progress = LoginProgress.Accessing
            authJob = viewModelScope.launch {
                val accessToken = authManager.setAuthorizationCode(state, code, startTime)
                loginCompleteChannel.send(accessToken)
            }
        }
    }

    private val loginCompleteChannel = Channel<String>(capacity = Channel.CONFLATED)
    val loginComplete = loginCompleteChannel.receiveAsFlow()
}

@Parcelize
enum class LoginProgress : Parcelable {
    Initializing, LoggingIn, Accessing
}
