package ninja.bryansills.loudping.session

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ninja.bryansills.loudping.di.ApplicationScope
import ninja.bryansills.loudping.storage.SimpleStorage

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
@Inject
class RealSessionManager(
  private val simpleStorage: SimpleStorage,
  @ApplicationScope private val coroutineScope: CoroutineScope,
) : SessionManager {
  override val currentSession: SharedFlow<Session> =
    simpleStorage[Stored.RefreshToken]
      .map { refreshToken ->
        if (refreshToken.isNotBlank()) {
          Session.LoggedIn(Uuid.random())
        } else {
          Session.LoggedOut
        }
      }
      .shareIn(scope = coroutineScope, started = SharingStarted.Lazily, replay = 1)

  override fun updateSession(block: (Session) -> Session) {
    //
  }
}
