package ninja.bryansills.loudping.app.core.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import ninja.bryansills.loudping.session.Session
import ninja.bryansills.loudping.session.SessionManager

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager,
) : ViewModel() {
    val currentSession: Deferred<Session> = viewModelScope.async {
        sessionManager.currentSession.first()
    }
}
