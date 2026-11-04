package ninja.bryansills.loudping

import android.app.Application
import androidx.work.Configuration
import com.bugsnag.android.Bugsnag
import dagger.hilt.android.HiltAndroidApp
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.android.MetroApplication

@HiltAndroidApp
class LoudPingApplication : Application(), Configuration.Provider, MetroApplication {

  private lateinit var androidAppGraph: AndroidAppGraph

  override val appComponentProviders: MetroAppComponentProviders
    get() = androidAppGraph

  override val workManagerConfiguration: Configuration
    get() {
      return Configuration.Builder().setWorkerFactory(androidAppGraph.workerFactory).build()
    }

  override fun onCreate() {
    super.onCreate()
    androidAppGraph = createGraphFactory<AndroidAppGraph.Factory>().create(this)
    Bugsnag.start(this)
  }
}
