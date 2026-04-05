package ninja.bryansills.loudping.jvm.network.runner

import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ninja.bryansills.loudping.coroutines.launchBlocking
import ninja.bryansills.loudping.database.LoudPingDatabase
import ninja.bryansills.loudping.database.RealDatabaseService
import ninja.bryansills.loudping.database.jvm.JvmSqlDriver
import ninja.bryansills.loudping.history.recorder.RealHistoryRecorder
import ninja.bryansills.loudping.network.RealGetRecentlyPlayed
import ninja.bryansills.loudping.network.RealNetworkService

fun main() {
    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    mainScope.launchBlocking {
        val spotifyService = initializeDependencies()
        val networkService = RealNetworkService(spotifyService)
        val getRecentlyPlayed = RealGetRecentlyPlayed(spotifyService)
        val sqlDriver = JvmSqlDriver()
        val sqlDatabase = LoudPingDatabase(sqlDriver)
        val databaseService = RealDatabaseService(sqlDatabase)
        val historyRecorder = RealHistoryRecorder(getRecentlyPlayed, databaseService)

        val start = Clock.System.now()
        val end = start - 5.days

        val syncedTo = historyRecorder(start, end)
        println("Synced tracks all the way back to $syncedTo")
    }
}
