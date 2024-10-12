package ninja.bryansills.loudping.app.core

import androidx.compose.runtime.Composable
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ninja.bryansills.loudping.app.core.navigation.DarkModeStatusBarDisposableEffect
import ninja.bryansills.loudping.app.theme.LoudPingTheme
import ninja.bryansills.loudping.ui.home.Home
import ninja.bryansills.loudping.ui.home.HomeScreen
import ninja.bryansills.loudping.ui.login.Login
import ninja.bryansills.loudping.ui.login.LoginActivity
import ninja.bryansills.loudping.ui.settings.Settings
import ninja.bryansills.loudping.ui.settings.SettingsScreen

@Composable
fun App() {
    LoudPingTheme {
        val navController = rememberNavController()
        DarkModeStatusBarDisposableEffect(navController)

        NavHost(navController = navController, startDestination = Home) {
            composable<Home> {
                HomeScreen(
                    onStartLogin = { navController.navigate(Login) },
                    onNavigateToSettings = { navController.navigate(Settings) },
                )
            }
            activity<Login> {
                activityClass = LoginActivity::class
            }
            composable<Settings> { SettingsScreen() }
        }
    }
}
