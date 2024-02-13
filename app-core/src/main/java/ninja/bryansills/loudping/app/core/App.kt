package ninja.bryansills.loudping.app.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ninja.bryansills.loudping.app.core.theme.LoudPingTheme

@Composable
fun App() {
    LoudPingTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                SplashScreen()
            }
        }
    }
}
