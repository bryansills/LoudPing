package ninja.bryansills.loudping.history.recorder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.network.NetworkService

class AlbumBatcher(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val coroutineScope: CoroutineScope,
    private val startAt: Instant,
    private val stopAt: Instant = Instant.DISTANT_PAST,
) {
    private val internalFlow = MutableSharedFlow<Pair<Instant, String>>(
        replay = 0,
        extraBufferCapacity = 1000,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    val stream = internalFlow.asSharedFlow()

    init {
        networkService.getRecentlyPlayedStream(startAt, stopAt)
    }
}
