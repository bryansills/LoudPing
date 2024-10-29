package ninja.bryansills.loudping.foreman

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.history.recorder.HistoryRecorder
import ninja.bryansills.loudping.storage.SimpleEntry
import ninja.bryansills.loudping.storage.SimpleStorage
import ninja.bryansills.loudping.time.TimeProvider

@HiltWorker
class HistoryRecorderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val historyRecorder: HistoryRecorder,
    private val simpleStorage: SimpleStorage,
    private val timeProvider: TimeProvider,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val currentTime = timeProvider.now
            val lastSyncedTo = simpleStorage[SyncedUpTo].map { Instant.parse(it) }.first()
            val result = historyRecorder(currentTime, lastSyncedTo)
            val successResult = result.getOrThrow()
            simpleStorage.update(SyncedUpTo) { currentTime.toString() }
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}

private val SyncedUpTo = SimpleEntry(
    stringPreferencesKey("tracks-synced-up-to"),
    "2000-01-01T09:00:00Z",
)
