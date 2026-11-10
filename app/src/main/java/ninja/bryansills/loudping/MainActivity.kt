package ninja.bryansills.loudping

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import ninja.bryansills.loudping.app.App

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
class MainActivity(
  private val viewModelFactory: MetroViewModelFactory,
) : ComponentActivity() {
  override val defaultViewModelProviderFactory: ViewModelProvider.Factory
    get() = viewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    setContent {
      CompositionLocalProvider(LocalMetroViewModelFactory provides viewModelFactory) {
        App()
      }
    }
  }
}
