package ninja.bryansills.loudping.ui.playedtracks

import android.content.Context
import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Instant
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
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tracks) { track ->
                        PlayedTrack(
                            title = track.trackTitle,
                            artist = "TODO Artist",
                            playedAt = track.timestamp,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayedTrack(
    title: String,
    artist: String,
    playedAt: Instant,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(LoudPingTheme.shapes.small)
                .background(LoudPingTheme.colorScheme.secondary)
                .size(56.dp),
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = LoudPingTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = artist,
                style = LoudPingTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Text(
            text = playedAt.relativeFormatted(),
            style = LoudPingTheme.typography.labelMedium,
            color = LoudPingTheme.colorScheme.onBackground.copy(alpha = 0.65f),
        )
    }
}

@Composable
private fun Instant.relativeFormatted(
    context: Context = LocalContext.current,
): String {
    return DateUtils
        .getRelativeDateTimeString(
            context,
            this.toEpochMilliseconds(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            0,
        )
        .toString()
}
