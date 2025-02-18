package ninja.bryansills.loudping.jvm.network.runner

import kotlin.time.Duration.Companion.days
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.datetime.Clock
import ninja.bryansills.loudping.coroutines.launchBlocking
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
    val end = start - 5.days

    val syncedTo = historyRecorder(start, end)
    println("Synced tracks all the way back to $syncedTo")
  }
}
