package ninja.bryansills.loudping.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.foreman.Foreman
import ninja.bryansills.loudping.foreman.ForemanJobs
import ninja.bryansills.loudping.network.auth.AuthManager
import ninja.bryansills.loudping.network.auth.RawAuthValues

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val foreman: Foreman,
) : ViewModel() {
    val uiState = combine(
        authManager.rawValues,
        foreman.jobStatus,
    ) { rawAuthValues, jobInfoMap ->
        SettingsUiState(
            rawAuthValues = rawAuthValues,
            jobDetails = jobInfoMap.entries.map { it.toJobDetails() },
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = SettingsUiState(
                rawAuthValues = RawAuthValues(
                    accessToken = "",
                    accessTokenExpiresAt = Instant.DISTANT_PAST,
                    refreshToken = "",
                ),
                jobDetails = listOf(),
            ),
        )
}

data class JobDetails(
    val loudPingId: String,
    val internalWorkManagerId: String,
    val status: WorkInfo.State,
    val nextAttemptAt: Instant?,
)

sealed interface JobStatus {
    data object Good : JobStatus

    data class Stopped(val reason: String) : JobStatus
}

data class SettingsUiState(
    val rawAuthValues: RawAuthValues,
    val jobDetails: List<JobDetails>,
)

private fun Map.Entry<ForemanJobs, WorkInfo>.toJobDetails(): JobDetails = JobDetails(
    loudPingId = this.key.workManagerId,
    internalWorkManagerId = this.value.id.toString(),
    status = this.value.state,
    nextAttemptAt = if (this.value.nextScheduleTimeMillis == Long.MAX_VALUE) {
        null
    } else {
        Instant.fromEpochMilliseconds(this.value.nextScheduleTimeMillis)
    },
)
