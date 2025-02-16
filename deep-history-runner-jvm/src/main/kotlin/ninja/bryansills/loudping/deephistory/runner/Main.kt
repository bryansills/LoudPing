package ninja.bryansills.loudping.deephistory.runner

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ninja.bryansills.loudping.coroutines.launchBlocking
import ninja.bryansills.loudping.deephistory.DeepHistoryRecord
import ninja.bryansills.loudping.deephistory.DeepHistoryRunEvent
import ninja.bryansills.loudping.deephistory.dash.initializeDependencies

fun main() {
    println("Starting work")
//    val records = getDeepHistory()
//    println("Loaded data into memory")
//    processData(records)

    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    mainScope.launchBlocking {
        val deps = initializeDependencies()

        deps.deepHistoryRunner(deps.deepHistoryDataProvider).collect { event ->
            when (event) {
                is DeepHistoryRunEvent.EntriesLoaded -> println(event)
                is DeepHistoryRunEvent.CachedChunk -> { }
                is DeepHistoryRunEvent.NetworkChunk -> println(event)
            }
        }
    }
}

private fun processData(records: List<DeepHistoryRecord>) {
    println("number of songs i've ever played: ${records.size}")

    val longest = records.maxBy { it.ms_played }
    println("longest song: $longest")

    val playCount = records.groupBy(
        keySelector = { it.spotify_track_uri },
        valueTransform = { "${it.master_metadata_album_artist_name} - ${it.master_metadata_track_name}" },
    )

    val top100 = playCount.values.sortedByDescending { it.count() }.take(100)

    top100.forEach { songData ->
        println("Song: ${songData.first()} Played: ${songData.count()}")
    }

    val longest20 = records.sortedByDescending { it.ms_played }.take(20)
    longest20.forEach {
        println("${it.master_metadata_album_artist_name} - ${it.master_metadata_track_name}")
    }

    val badData = records.filter {
        it.master_metadata_track_name == null ||
            it.master_metadata_album_artist_name == null ||
            it.master_metadata_album_album_name == null ||
            it.spotify_track_uri == null
    }
    badData.forEach { println(it) }
}
