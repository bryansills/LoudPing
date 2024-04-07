package ninja.bryansills.loudping.jvm.network.runner

import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.exitProcess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking

fun main() {
    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    mainScope.launchBlocking {
        val spotifyService = initializeDependencies()

        val response = spotifyService.getMe()
        println(response.toString())
    }
}

private fun CoroutineScope.launchBlocking(block: suspend CoroutineScope.() -> Unit) {
    try {
        runBlocking(this.coroutineContext) {
            block()
            this.cancel("Time to die.")
        }
    } catch (_: CancellationException) {
    }

    exitProcess(0)
}
