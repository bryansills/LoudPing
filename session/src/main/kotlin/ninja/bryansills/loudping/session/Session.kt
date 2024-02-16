package ninja.bryansills.loudping.session

import java.util.UUID

sealed interface Session {
    data object LoggedOut : Session

    data class LoggedIn(val id: UUID) : Session
}
