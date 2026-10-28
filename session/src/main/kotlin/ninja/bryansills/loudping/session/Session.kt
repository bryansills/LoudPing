package ninja.bryansills.loudping.session

import kotlin.uuid.Uuid

sealed interface Session {
  data object LoggedOut : Session

  data class LoggedIn(val id: Uuid) : Session
}
