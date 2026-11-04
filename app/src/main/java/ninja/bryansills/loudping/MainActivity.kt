package ninja.bryansills.loudping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import ninja.bryansills.loudping.app.App

@AndroidEntryPoint
class MainActivity(
  private val viewModelFactory: LoudPingViewModelFactory,
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
