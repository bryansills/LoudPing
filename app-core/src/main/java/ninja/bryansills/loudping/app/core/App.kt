package ninja.bryansills.loudping.app.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ninja.bryansills.loudping.app.core.home.HomeScreen
import ninja.bryansills.loudping.app.core.login.LoginScreen
import ninja.bryansills.loudping.app.core.navigation.DarkModeStatusBarDisposableEffect
import ninja.bryansills.loudping.app.core.navigation.DarkModeStatusBarNavArg
import ninja.bryansills.loudping.app.core.settings.SettingsScreen
import ninja.bryansills.loudping.app.core.splash.SplashScreen
import ninja.bryansills.loudping.app.theme.LoudPingTheme
import ninja.bryansills.loudping.session.Session

@Composable
fun App() {
    LoudPingTheme {
        val navController = rememberNavController()
        DarkModeStatusBarDisposableEffect(navController)

        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                SplashScreen { session ->
                    when (session) {
                        is Session.LoggedIn -> navController.navigate("home")
                        Session.LoggedOut -> navController.navigate("login")
                    }
                }
            }
            composable(
                route = "login",
                arguments = listOf(
                    navArgument(DarkModeStatusBarNavArg) { defaultValue = true },
                ),
            ) {
                LoginScreen { session ->
                    when (session) {
                        is Session.LoggedIn -> navController.navigate("home")
                        Session.LoggedOut -> navController.navigate("login")
                    }
                }
            }
            composable("home") {
                HomeScreen { navController.navigate("settings") }
            }
            composable("settings") {
                SettingsScreen()
            }
        }
    }
}
