package ninja.bryansills.loudping.app.core.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.network.auth.AuthManager
import ninja.bryansills.loudping.time.TimeProvider

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val timeProvider: TimeProvider,
) : ViewModel() {
    private val startTime: Instant = timeProvider.now

    val loginUrl: String = authManager.getAuthorizeUrl(startTime)

    suspend fun setAuthorizationCode(
        state: String,
        code: String,
    ): String = coroutineScope {
        withContext(Dispatchers.Main) {
            authManager.setAuthorizationCode(state, code, startTime)
        }
    }
}
