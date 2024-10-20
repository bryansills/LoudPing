package ninja.bryansills.loudping.jvm.network.runner

import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.hours
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import ninja.bryansills.loudping.database.DriverFactory
import ninja.bryansills.loudping.database.RealDatabaseService
import ninja.bryansills.loudping.database.createDatabase
import ninja.bryansills.loudping.history.recorder.RealHistoryRecorder
import ninja.bryansills.loudping.network.RealNetworkService

fun main() {
    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    mainScope.launchBlocking {
        val spotifyService = initializeDependencies()
        val networkService = RealNetworkService(spotifyService)
        val databaseService = RealDatabaseService(createDatabase(DriverFactory()))
        val historyRecorder = RealHistoryRecorder(networkService, databaseService)

        val start = Clock.System.now()
        val end = start - 5.hours

        val syncedTo = historyRecorder(start, end)
        println("Synced tracks all the way back to $syncedTo")
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
