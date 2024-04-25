package ninja.bryansills.loudping.jvm.network.runner

import kotlin.system.exitProcess
import kotlinx.coroutines.CancellationException
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

        val recentlyPlayed = spotifyService.getRecentlyPlayed()
        println(recentlyPlayed.toString())

        val savedAlbums = spotifyService.getSavedAlbums()
        println(savedAlbums.toString())
    }
}

/**
 * Do some work and then kill the process. We don't want Android Studio to think that we are still
 * waiting for work to finish.
 */
private fun CoroutineScope.launchBlocking(block: suspend CoroutineScope.() -> Unit) {
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
