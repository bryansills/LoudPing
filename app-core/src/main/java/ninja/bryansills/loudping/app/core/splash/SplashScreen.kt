package ninja.bryansills.loudping.app.core.splash

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val session = splashViewModel.currentSession.await()
        Toast.makeText(context, session.toString(), Toast.LENGTH_LONG).show()
    }

    Box(modifier = modifier.padding(64.dp)) {
        Text("Starting up...")
    }
}
