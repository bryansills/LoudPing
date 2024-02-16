package ninja.bryansills.loudping.session

import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ninja.bryansills.loudping.di.ApplicationScope
import ninja.bryansills.loudping.storage.SimpleStorage

class RealSessionManager @Inject constructor(
    private val simpleStorage: SimpleStorage,
    @ApplicationScope private val coroutineScope: CoroutineScope,
) : SessionManager {
    override val currentSession: SharedFlow<Session> = simpleStorage[Stored.RefreshToken]
        .map {
            delay(2000)
            Session.LoggedIn(UUID.randomUUID())
        }
        .shareIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            replay = 1,
        )

    override fun updateSession(block: (Session) -> Session) {
        //
    }
}
