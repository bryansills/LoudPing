package ninja.bryansills.loudping.foreman

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface Foreman {
  val jobStatus: Flow<Map<ForemanJobs, WorkInfo>>
}
