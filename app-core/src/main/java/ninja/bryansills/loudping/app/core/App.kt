package ninja.bryansills.loudping.app.core

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ninja.bryansills.loudping.app.core.home.HomeScreen
import ninja.bryansills.loudping.app.core.login.LoginScreen
import ninja.bryansills.loudping.app.core.splash.SplashScreen
import ninja.bryansills.loudping.app.core.theme.LoudPingTheme
import ninja.bryansills.loudping.session.Session

@Composable
fun App() {
    LoudPingTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                SplashScreen { session ->
                    Log.d("BLARG", session.toString())
                    when (session) {
                        is Session.LoggedIn -> navController.navigate("home")
                        Session.LoggedOut -> navController.navigate("login")
                    }
                }
            }
            composable("login") {
                LoginScreen { session ->
                    Log.d("BLARG", session.toString())
                    when (session) {
                        is Session.LoggedIn -> navController.navigate("home")
                        Session.LoggedOut -> navController.navigate("login")
                    }
                }
            }
            composable("home") {
                HomeScreen()
            }
        }
    }
}
