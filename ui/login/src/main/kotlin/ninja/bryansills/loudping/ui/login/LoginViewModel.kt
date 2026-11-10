package ninja.bryansills.loudping.ui.login

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlin.time.Instant
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import ninja.bryansills.loudping.network.auth.AuthManager
import ninja.bryansills.loudping.time.TimeProvider

@AssistedInject
class LoginViewModel(
  @Assisted private val savedStateHandle: SavedStateHandle,
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

  fun setAuthorizationCode(code: String, state: String) {
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

  @AssistedFactory
  @ViewModelAssistedFactoryKey(LoginViewModel::class)
  @ContributesIntoMap(AppScope::class)
  fun interface Factory : ViewModelAssistedFactory {
    override fun create(extras: CreationExtras): LoginViewModel {
      return create(extras.createSavedStateHandle())
    }

    fun create(@Assisted savedStateHandle: SavedStateHandle): LoginViewModel
  }
}

@Parcelize
enum class LoginProgress : Parcelable {
  Initializing,
  LoggingIn,
  Accessing,
}
