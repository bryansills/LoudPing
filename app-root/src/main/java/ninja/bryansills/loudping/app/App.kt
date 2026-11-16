package ninja.bryansills.loudping.app

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import ninja.bryansills.loudping.app.theme.LoudPingTheme
import ninja.bryansills.loudping.ui.home.Home
import ninja.bryansills.loudping.ui.home.HomeScreen
import ninja.bryansills.loudping.ui.login.Login
import ninja.bryansills.loudping.ui.playedtracks.PlayedTracks
import ninja.bryansills.loudping.ui.playedtracks.PlayedTracksScreen
import ninja.bryansills.loudping.ui.refreshtokenentry.RefreshTokenEntry
import ninja.bryansills.loudping.ui.refreshtokenentry.refreshTokenEntry
import ninja.bryansills.loudping.ui.settings.Settings
import ninja.bryansills.loudping.ui.settings.SettingsScreen

@Composable
fun App() {
  LoudPingTheme {
    val backstack = rememberNavBackStack(Home)
    // TODO: get this back up and working?
    // DarkModeStatusBarDisposableEffect(navController)

    NavDisplay(
      entryDecorators =
        listOf(
          rememberSaveableStateHolderNavEntryDecorator(),
          rememberViewModelStoreNavEntryDecorator(),
        ),
      backStack = backstack,
      onBack = backstack::removeLastOrNull,
      entryProvider =
        entryProvider {
          entry<Home> {
            HomeScreen(
              onStartLogin = { backstack.add(Login) },
              toRefreshTokenEntry = { backstack.add(RefreshTokenEntry) },
              onNavigateToPlayedTracks = { backstack.add(PlayedTracks) },
              onNavigateToSettings = { backstack.add(Settings) },
            )
          }
          // TODO: login
          // activity<Login> { activityClass = (LoginActivity::class as KClass<out Activity>) }
          entry<Settings> { SettingsScreen() }
          entry<PlayedTracks> { PlayedTracksScreen() }
          refreshTokenEntry(onDismiss = backstack::removeLastOrNull)
        },
    )
  }
}
