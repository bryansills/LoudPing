package ninja.bryansills.loudping.foreman

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metro.sample.android.MetroWorkerFactory
import dev.zacsweers.metro.sample.android.WorkerKey
import kotlin.time.Instant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ninja.bryansills.loudping.history.recorder.HistoryRecorder
import ninja.bryansills.loudping.logger.Logger
import ninja.bryansills.loudping.logger.e
import ninja.bryansills.loudping.storage.SimpleEntry
import ninja.bryansills.loudping.storage.SimpleStorage
import ninja.bryansills.loudping.time.TimeProvider

@AssistedInject
class HistoryRecorderWorker(
  appContext: Context,
  @Assisted params: WorkerParameters,
  private val historyRecorder: HistoryRecorder,
  private val simpleStorage: SimpleStorage,
  private val timeProvider: TimeProvider,
  private val logger: Logger,
) : CoroutineWorker(appContext, params) {
  override suspend fun doWork(): Result =
    try {
      val currentTime = timeProvider.now
      val lastSyncedTo = simpleStorage[SyncedUpTo].map { Instant.parse(it) }.first()
      val result = historyRecorder(currentTime, lastSyncedTo)
      val successResult = result.getOrThrow()
      simpleStorage.update(SyncedUpTo) { currentTime.toString() }
      Result.success()
    } catch (ex: Exception) {
      logger.e(ex)
      Result.failure(workDataOf("CAUSE" to ex.localizedMessage))
    }

  @WorkerKey(HistoryRecorderWorker::class)
  @ContributesIntoMap(
    AppScope::class,
    binding = binding<MetroWorkerFactory.WorkerInstanceFactory<*>>(),
  )
  @AssistedFactory
  abstract class Factory : MetroWorkerFactory.WorkerInstanceFactory<HistoryRecorderWorker>
}

private val SyncedUpTo =
  SimpleEntry(stringPreferencesKey("tracks-synced-up-to"), "2000-01-01T09:00:00Z")
