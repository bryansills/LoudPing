package ninja.bryansills.loudping.session

import kotlinx.coroutines.flow.SharedFlow

interface SessionManager {
    val currentSession: SharedFlow<Session>

    fun updateSession(block: (Session) -> Session)
}
