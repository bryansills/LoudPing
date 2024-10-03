package ninja.bryansills.loudping.app.core.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ninja.bryansills.loudping.app.theme.LoudPingTheme
import ninja.bryansills.loudping.session.Session

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = hiltViewModel(),
    navigate: (Session) -> Unit,
) {
    val invokeNavigate by rememberUpdatedState(navigate)
    LaunchedEffect(Unit) {
        val session = splashViewModel.currentSession.await()
        invokeNavigate(session)
    }

    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically,
            ),
            modifier = Modifier.consumeWindowInsets(paddingValues).fillMaxSize(),
        ) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
            Text(
                text = "Starting up...",
                style = LoudPingTheme.typography.labelLarge,
            )
        }
    }
}
