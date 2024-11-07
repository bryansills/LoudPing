package ninja.bryansills.loudping.app.core

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.reflect.KClass
import ninja.bryansills.loudping.app.core.navigation.DarkModeStatusBarDisposableEffect
import ninja.bryansills.loudping.app.theme.LoudPingTheme
import ninja.bryansills.loudping.ui.home.Home
import ninja.bryansills.loudping.ui.home.HomeScreen
import ninja.bryansills.loudping.ui.login.Login
import ninja.bryansills.loudping.ui.login.LoginActivity
import ninja.bryansills.loudping.ui.playedtracks.PlayedTracks
import ninja.bryansills.loudping.ui.playedtracks.PlayedTracksScreen
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
                    onNavigateToPlayedTracks = { navController.navigate(PlayedTracks) },
                    onNavigateToSettings = { navController.navigate(Settings) },
                )
            }
            activity<Login> {
                activityClass = (LoginActivity::class as KClass<out Activity>)
            }
            composable<Settings> { SettingsScreen() }
            composable<PlayedTracks> { PlayedTracksScreen() }
        }
    }
}
