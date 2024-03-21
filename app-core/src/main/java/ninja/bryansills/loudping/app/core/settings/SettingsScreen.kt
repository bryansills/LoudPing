package ninja.bryansills.loudping.app.core.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ninja.bryansills.loudping.app.core.theme.LoudPingTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .fillMaxSize(),
        ) {
            Text(
                text = "This is the settings screen",
                style = LoudPingTheme.typography.labelLarge,
            )
        }
    }
}
