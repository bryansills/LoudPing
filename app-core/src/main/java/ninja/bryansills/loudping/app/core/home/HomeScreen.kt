package ninja.bryansills.loudping.app.core.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ninja.bryansills.loudping.app.core.theme.LoudPingTheme
import ninja.bryansills.loudping.res.R as AppR

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            IconButton(
                onClick = onNavigateToSettings,
                modifier = Modifier.align(Alignment.BottomEnd),
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(AppR.string.settings),
                )
            }
        }
    }
}

@Composable
private fun AlbumsBox(
    albums: List<PlaceholderAlbum>,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = LoudPingTheme.shapes.large,
        tonalElevation = 4.dp,
        modifier = modifier,
    ) {
        Column {
            albums.forEach { album ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .clip(LoudPingTheme.shapes.small)
                            .background(Color.Cyan)
                            .size(48.dp),
                    )

                    Column {
                        Text(
                            text = album.title,
                            style = LoudPingTheme.typography.bodyMedium,
                        )
                        Text(
                            text = album.artist,
                            style = LoudPingTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}

private data class PlaceholderAlbum(
    val artist: String,
    val title: String,
)

@Preview
@Composable
private fun AlbumsBoxPreview() {
    val albums = listOf(
        PlaceholderAlbum("Jeff Bob", "A Really Cool Album"),
        PlaceholderAlbum("Annie Hart", "Not Her Best"),
        PlaceholderAlbum("Bernice Griffin", "Whoops! All Bangers"),
        PlaceholderAlbum("Rex Shepard", "Late Career Revival"),
        PlaceholderAlbum("Steve Hawthorn", "Let's Not Talk About This One"),
    )
    AlbumsBox(
        albums = albums,
        modifier = Modifier.widthIn(max = 400.dp),
    )
}
