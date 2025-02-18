package ninja.bryansills.loudping.session

import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ninja.bryansills.loudping.storage.SimpleStorage

class RealSessionManager(
    private val simpleStorage: SimpleStorage,
    private val coroutineScope: CoroutineScope,
) : SessionManager {
    override val currentSession: SharedFlow<Session> =
        simpleStorage[Stored.RefreshToken]
            .map { refreshToken ->
                if (refreshToken.isNotBlank()) {
                    Session.LoggedIn(UUID.randomUUID())
                } else {
                    Session.LoggedOut
                }
            }
            .shareIn(scope = coroutineScope, started = SharingStarted.Lazily, replay = 1)

    override fun updateSession(block: (Session) -> Session) {
        //
    }
}
