package ninja.bryansills.loudping

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.bugsnag.android.Bugsnag
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LoudPingApplication : Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() {
            return Configuration.Builder().setWorkerFactory(workerFactory).build()
        }

    override fun onCreate() {
        super.onCreate()
        Bugsnag.start(this)
    }
}
