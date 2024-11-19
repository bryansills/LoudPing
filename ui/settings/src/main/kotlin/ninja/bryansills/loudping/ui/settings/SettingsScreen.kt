package ninja.bryansills.loudping.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import ninja.bryansills.loudping.app.theme.LoudPingTheme
import ninja.bryansills.loudping.res.R as AppR

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            val uiState by settingsViewModel.uiState.collectAsState()
            SettingsItem(
                headline = stringResource(AppR.string.refresh_token),
                value = uiState.rawAuthValues.refreshToken,
                onCopyToClipboard = {
                    clipboardManager.setText(AnnotatedString(uiState.rawAuthValues.refreshToken))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            SettingsItem(
                headline = stringResource(AppR.string.access_token),
                value = uiState.rawAuthValues.accessToken,
                subValue = stringResource(
                    AppR.string.expires_at,
                    uiState.rawAuthValues.accessTokenExpiresAt.format(),
                ),
                onCopyToClipboard = {
                    clipboardManager.setText(AnnotatedString(uiState.rawAuthValues.accessToken))
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(AppR.string.background_work),
                style = LoudPingTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            )

            uiState.jobDetails.forEach { jobDetail ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = jobDetail.loudPingId,
                        style = LoudPingTheme.typography.bodyMedium,
                    )
                    Text(
                        text = jobDetail.internalWorkManagerId,
                        style = LoudPingTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(AppR.string.job_status, jobDetail.status.name),
                        style = LoudPingTheme.typography.bodySmall,
                    )
                    Text(
                        text = if (jobDetail.nextAttemptAt != null) {
                            stringResource(AppR.string.next_run_at, jobDetail.nextAttemptAt.format())
                        } else {
                            stringResource(AppR.string.no_future_runs)
                        },
                        style = LoudPingTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsItem(
    headline: String,
    value: String,
    onCopyToClipboard: () -> Unit,
    modifier: Modifier = Modifier,
    subValue: String? = null,
) {
    Row(modifier = modifier.padding(16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = headline,
                style = LoudPingTheme.typography.bodyLarge,
            )
            Text(
                text = value,
                style = LoudPingTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp),
            )
            if (subValue != null) {
                Text(
                    text = subValue,
                    style = LoudPingTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
        IconButton(
            onClick = onCopyToClipboard,
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Icon(
                painter = painterResource(AppR.drawable.icon_copy),
                contentDescription = stringResource(AppR.string.copy_to_clipboard),
            )
        }
    }
}

private fun Instant.format(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    return this
        .toLocalDateTime(timeZone)
        .format(LocalDateTime.Formats.ISO)
}
