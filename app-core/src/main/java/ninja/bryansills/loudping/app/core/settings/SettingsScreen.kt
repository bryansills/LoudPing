package ninja.bryansills.loudping.app.core.settings

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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import ninja.bryansills.loudping.app.core.theme.LoudPingTheme
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
            uiState.accessTokenExpiresAt
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .format(LocalDateTime.Formats.ISO)

            SettingsItem(
                headline = stringResource(AppR.string.refresh_token),
                value = uiState.refreshToken,
                onCopyToClipboard = {
                    clipboardManager.setText(AnnotatedString(uiState.refreshToken))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            SettingsItem(
                headline = stringResource(AppR.string.access_token),
                value = uiState.accessToken,
                subValue = stringResource(
                    AppR.string.expires_at,
                    uiState.accessTokenExpiresAt
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .format(LocalDateTime.Formats.ISO),
                ),
                onCopyToClipboard = {
                    clipboardManager.setText(AnnotatedString(uiState.accessToken))
                },
                modifier = Modifier.fillMaxWidth(),
            )
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
