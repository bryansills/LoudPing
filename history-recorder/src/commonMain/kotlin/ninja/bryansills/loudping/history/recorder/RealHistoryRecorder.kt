package ninja.bryansills.loudping.history.recorder

import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.network.NetworkService

class RealHistoryRecorder(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
) : HistoryRecorder {
    override suspend fun invoke(
        startAt: Instant,
        stopAt: Instant?,
    ): Result<Instant> = coroutineScope {
        if (stopAt != null) {
            check(startAt > stopAt)
        }
        TODO("Not yet implemented")
    }
}
