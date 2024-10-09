package ninja.bryansills.loudping.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.app.theme.LoudPingTheme

@Serializable
object Home

@Composable
fun HomeScreen(
    onStartLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier) { paddingValues ->
        val screenModifier = Modifier.padding(paddingValues).fillMaxSize()

        when (uiState) {
            HomeUiState.Loading -> HomeLoadingScreen(modifier = screenModifier)
            HomeUiState.LoggedIn -> HomeLoggedInScreen(modifier = screenModifier)
            HomeUiState.LoggedOut -> HomeLoggedOutScreen(
                onStartLogin = onStartLogin,
                modifier = screenModifier
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
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            alignment = Alignment.CenterVertically,
        ),
        modifier = modifier,
    ) {
        Text(
            text = "You should log in...",
            style = LoudPingTheme.typography.labelLarge,
        )
        Button(
            onClick = onStartLogin
        ) {
            Text(text = "Log in")
        }
    }
}


@Composable
private fun HomeLoggedInScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = "Logged in...",
            style = LoudPingTheme.typography.labelLarge,
        )
    }
}
