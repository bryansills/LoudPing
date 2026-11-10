package ninja.bryansills.loudping.foreman

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import java.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ContributesBinding(AppScope::class)
@Inject
class DefaultForeman(workManager: WorkManager) : Foreman {
  init {
    workManager.enqueueUniquePeriodicWork(
      ForemanJobs.TrackSyncing.workManagerId,
      ExistingPeriodicWorkPolicy.KEEP,
      PeriodicWorkRequestBuilder<HistoryRecorderWorker>(Duration.ofHours(2)).build(),
    )
  }

  override val jobStatus: Flow<Map<ForemanJobs, WorkInfo>> =
    workManager.getWorkInfosForUniqueWorkFlow(ForemanJobs.TrackSyncing.workManagerId).map {
      mapOf(ForemanJobs.TrackSyncing to it.first())
    }
}

enum class ForemanJobs(val workManagerId: String) {
  TrackSyncing("loud-ping-track-syncing"),
}
