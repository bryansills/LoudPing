package ninja.bryansills.loudping.ui.settings

import android.content.ClipData
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlin.time.Instant
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import ninja.bryansills.loudping.app.theme.LoudPingTheme
import ninja.bryansills.loudping.ui.android_app_res.R as AppR

@Composable
fun SettingsScreen(
  modifier: Modifier = Modifier,
  settingsViewModel: SettingsViewModel = metroViewModel(),
  clipboardManager: Clipboard = LocalClipboard.current,
) {
  val coroutineScope = rememberCoroutineScope()
  Scaffold(modifier = modifier) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
      val uiState by settingsViewModel.uiState.collectAsState()
      SettingsItem(
        headline = stringResource(AppR.string.refresh_token),
        value = uiState.rawAuthValues.refreshToken,
        onCopyToClipboard = {
          coroutineScope.launch {
            val refreshTokenValue = uiState.rawAuthValues.refreshToken
            val trimmedValue =
              if (refreshTokenValue.length >= 6) {
                "${refreshTokenValue.take(6)}..."
              } else {
                refreshTokenValue
              }
            clipboardManager.setClipEntry(
              ClipEntry(ClipData.newPlainText(trimmedValue, refreshTokenValue)),
            )
          }
        },
        modifier = Modifier.fillMaxWidth(),
      )
      SettingsItem(
        headline = stringResource(AppR.string.access_token),
        value = uiState.rawAuthValues.accessToken,
        subValue =
          stringResource(
            AppR.string.expires_at,
            uiState.rawAuthValues.accessTokenExpiresAt.format(),
          ),
        onCopyToClipboard = {
          coroutineScope.launch {
            val accessTokenValue = uiState.rawAuthValues.accessToken
            val trimmedValue =
              if (accessTokenValue.length >= 6) {
                "${accessTokenValue.take(6)}..."
              } else {
                accessTokenValue
              }
            clipboardManager.setClipEntry(
              ClipEntry(ClipData.newPlainText(trimmedValue, accessTokenValue)),
            )
          }
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
          Text(text = jobDetail.loudPingId, style = LoudPingTheme.typography.bodyMedium)
          Text(text = jobDetail.internalWorkManagerId, style = LoudPingTheme.typography.bodySmall)
          Text(
            text = stringResource(AppR.string.job_status, jobDetail.status.name),
            style = LoudPingTheme.typography.bodySmall,
          )
          Text(
            text =
              if (jobDetail.nextAttemptAt != null) {
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
      Text(text = headline, style = LoudPingTheme.typography.bodyLarge)
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
    IconButton(onClick = onCopyToClipboard, modifier = Modifier.align(Alignment.CenterVertically)) {
      Icon(
        painter = painterResource(AppR.drawable.icon_copy),
        contentDescription = stringResource(AppR.string.copy_to_clipboard),
      )
    }
  }
}

private fun Instant.format(timeZone: TimeZone = TimeZone.currentSystemDefault()): String =
  this.toLocalDateTime(timeZone).format(LocalDateTime.Formats.ISO)
