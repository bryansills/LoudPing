package ninja.bryansills.loudping.app.core.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import ninja.bryansills.loudping.session.Session

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = hiltViewModel(),
    navigate: (Session) -> Unit
) {
    val invokeNavigate by rememberUpdatedState(navigate)
    LaunchedEffect(Unit) {
        val session = splashViewModel.currentSession.await()
        invokeNavigate(session)
    }

    Box(modifier = modifier.background(Color.LightGray).fillMaxSize()) {
        Text("Starting up...")
    }
}
