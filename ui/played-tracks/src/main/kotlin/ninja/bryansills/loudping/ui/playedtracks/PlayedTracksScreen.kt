package ninja.bryansills.loudping.ui.playedtracks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ninja.bryansills.loudping.app.theme.LoudPingTheme

@Composable
fun PlayedTracksScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayedTracksViewModel = hiltViewModel(),
) {
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier) { paddingValues ->
        val screenModifier = Modifier.padding(paddingValues).fillMaxSize()

        Column(modifier = screenModifier) {
            if (tracks.isEmpty()) {
                Text(text = "Nothing played...")
            }

            tracks.forEach { record ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = record.trackTitle,
                        style = LoudPingTheme.typography.titleLarge,
                    )
                    Text(
                        text = record.trackNumber.toString(),
                        style = LoudPingTheme.typography.titleSmall,
                    )
                }
            }
        }
    }
}
