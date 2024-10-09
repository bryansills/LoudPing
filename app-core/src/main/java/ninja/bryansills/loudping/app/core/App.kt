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

@Composable
fun App() {
    LoudPingTheme {
        val navController = rememberNavController()
        DarkModeStatusBarDisposableEffect(navController)

        NavHost(navController = navController, startDestination = Home) {
            composable<Home> { HomeScreen(
                onStartLogin = { navController.navigate(Login) }
            ) }
            activity<Login> {
                activityClass = LoginActivity::class
            }
//            composable("splash") {
//                SplashScreen { session ->
//                    when (session) {
//                        is Session.LoggedIn -> navController.navigate("home")
//                        Session.LoggedOut -> navController.navigate("login")
//                    }
//                }
//            }
//            composable(
//                route = "login",
//                arguments = listOf(
//                    navArgument(DarkModeStatusBarNavArg) { defaultValue = true },
//                ),
//            ) {
//                LoginScreen { session ->
//                    when (session) {
//                        is Session.LoggedIn -> navController.navigate("home")
//                        Session.LoggedOut -> navController.navigate("login")
//                    }
//                }
//            }
//            composable("home") {
//                HomeScreen { navController.navigate("settings") }
//            }
//            composable("settings") {
//                SettingsScreen()
//            }
        }
    }
}
