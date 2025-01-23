package ninja.bryansills.loudping.deephistory.runner

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.coroutines.launchBlocking
import ninja.bryansills.loudping.deephistory.DeepHistoryRecord
import ninja.bryansills.loudping.deephistory.base62Uri
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main() {
    val records = getDeepHistory()
//    processData(records)

    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    mainScope.launchBlocking {
        val deps = initializeDependencies()

        val recordsWithDatabase = records
            .take(50)
            .associateWith { deepRecord ->
                val cachedTrack = deps.trackRepo.getTrackBySpotifyId(deepRecord.base62Uri)
                cachedTrack
            }

        val recordsWithMissing = recordsWithDatabase
            .filter { (_, cachedTrack) ->
                cachedTrack == null
            }
            .map { (deepRecord, _) -> deepRecord }

        val chunkedMissing = recordsWithMissing.chunked(50)

        val recordsFromNetwork = chunkedMissing
            .flatMap { recordsWindow ->
                val ids = recordsWindow.map { it.base62Uri }
                val repoTracks = deps.trackRepo.getTracksBySpotifyIds(ids)
                recordsWindow.map { indRecord ->
                    val matchingTrack = repoTracks.find {
                        it.spotifyId == indRecord.base62Uri
                    }!!
                    indRecord to matchingTrack
                }
            }
            .toMap()

        recordsWithDatabase.forEach { (record, track) ->
            println("Record: $record Track: $track")
        }
        println("----------")
        recordsFromNetwork.forEach { (record, track) ->
            println("Record: $record Track: $track")
        }
    }
}

private fun getDeepHistory(): List<DeepHistoryRecord> {
    val jsonPaths = FileSystem.RESOURCES.list(".".toPath())
        .filter { path ->
            "json" == path.name.split(".").lastOrNull()?.lowercase()
        }

    val jsonText = jsonPaths
        .map { path ->
            FileSystem.RESOURCES.source(path).use { fileSource ->
                fileSource.buffer().readUtf8()
            }
        }

    val json = Json { ignoreUnknownKeys = true }

    return jsonText.flatMap { text ->
        json.decodeFromString<List<DeepHistoryRecord>>(text)
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
