package ninja.bryansills.loudping.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ninja.bryansills.loudping.android_app_res.R
import ninja.bryansills.loudping.app.theme.LoudPingTheme

@Composable
fun HomeScreen(
    onStartLogin: () -> Unit,
    toRefreshTokenEntry: () -> Unit,
    onNavigateToPlayedTracks: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier) { paddingValues ->
        val screenModifier = Modifier.padding(paddingValues).fillMaxSize()

        when (uiState) {
            HomeUiState.Loading -> HomeLoadingScreen(modifier = screenModifier)
            HomeUiState.LoggedIn -> {
                HomeLoggedInScreen(
                    onNavigateToPlayedTracks = onNavigateToPlayedTracks,
                    onNavigateToSettings = onNavigateToSettings,
                    modifier = screenModifier,
                )
            }
            HomeUiState.LoggedOut -> HomeLoggedOutScreen(
                onStartLogin = onStartLogin,
                toRefreshTokenEntry = toRefreshTokenEntry,
                modifier = screenModifier,
            )
        }
    }
}

@Composable
private fun HomeLoadingScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            alignment = Alignment.CenterVertically,
        ),
        modifier = modifier,
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
        Text(
            text = "Starting up...",
            style = LoudPingTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun HomeLoggedOutScreen(
    onStartLogin: () -> Unit,
    toRefreshTokenEntry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.padding(16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically,
            ),
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                text = "You should log in...",
                style = LoudPingTheme.typography.labelLarge,
            )
            Button(
                onClick = onStartLogin,
            ) {
                Text(text = "Log in")
            }
        }

        IconButton(
            onClick = toRefreshTokenEntry,
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = stringResource(R.string.settings),
            )
        }
    }
}
