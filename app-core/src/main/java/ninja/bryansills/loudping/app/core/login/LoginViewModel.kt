package ninja.bryansills.loudping.app.core.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.network.auth.AuthManager
import ninja.bryansills.loudping.time.TimeProvider

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val timeProvider: TimeProvider,
) : ViewModel() {
    val now: Instant
        get() = timeProvider.now

    fun getLoginUrl(startTime: Instant) = authManager.getAuthorizeUrl(startTime)

    suspend fun setAuthorizationCode(
        state: String,
        code: String,
        startTime: Instant,
    ) = authManager.setAuthorizationCode(state, code, startTime)
}
