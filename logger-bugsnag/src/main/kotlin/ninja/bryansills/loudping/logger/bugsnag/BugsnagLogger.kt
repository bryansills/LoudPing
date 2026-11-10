package ninja.bryansills.loudping.logger.bugsnag

import android.util.Log
import com.bugsnag.android.Bugsnag
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import ninja.bryansills.loudping.logger.Logger

@ContributesBinding(AppScope::class)
@Inject
class BugsnagLogger : Logger {
  override fun e(message: String, ex: Throwable?) {
    if (Bugsnag.isStarted()) {
      Bugsnag.leaveBreadcrumb(message)
      val loggedMessage = ex ?: RuntimeException(message)
      Bugsnag.notify(loggedMessage)
    } else {
      Log.e("Loud Ping", message, ex)
    }
  }
}
