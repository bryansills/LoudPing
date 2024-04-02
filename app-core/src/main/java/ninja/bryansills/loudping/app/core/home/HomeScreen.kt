package ninja.bryansills.loudping.app.core.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
private fun AlbumsBox(modifier: Modifier = Modifier) {
    Surface(
        shape = LoudPingTheme.shapes.large,
        tonalElevation = 4.dp,
        modifier = modifier,
    ) {
    }
}

private data class PlaceholderAlbum(
    @StringRes val artist: Int,
    @StringRes val albumTitle: Int,
    @DrawableRes val art: Int,
)
