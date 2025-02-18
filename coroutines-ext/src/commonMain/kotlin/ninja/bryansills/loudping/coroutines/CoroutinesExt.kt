package ninja.bryansills.loudping.coroutines

import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.exitProcess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking

/**
 * Do some work and then kill the process. We don't want Android Studio to think that we are still
 * waiting for work to finish.
 */
fun CoroutineScope.launchBlocking(block: suspend CoroutineScope.() -> Unit) {
  try {
    runBlocking(this.coroutineContext) {
      block()
      this.cancel("Time to die.")
    }
  } catch (ex: Exception) {
    if (ex !is CancellationException) {
      ex.printStackTrace()
    }
  }

  exitProcess(0)
}
