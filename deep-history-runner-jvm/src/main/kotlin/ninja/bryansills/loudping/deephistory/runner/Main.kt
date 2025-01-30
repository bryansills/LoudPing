package ninja.bryansills.loudping.deephistory.runner

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.coroutines.launchBlocking
import ninja.bryansills.loudping.database.model.Track
import ninja.bryansills.loudping.deephistory.DeepHistoryRecord
import ninja.bryansills.loudping.deephistory.base62Uri
import ninja.bryansills.loudping.repository.track.MultiTrackResult
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main() {
    println("Starting work")
    val records = getDeepHistory()
    println("Loaded data into memory")
//    processData(records)

    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    mainScope.launchBlocking {
        val deps = initializeDependencies()

        println("Dependencies initialized")

        val recordsWithCachedData = records
            .associateWith { deepRecord ->
                val cachedTrack = deps.trackRepo.getTrackBySpotifyId(deepRecord.base62Uri)
                cachedTrack
            }

        println("Got all the cached data")

        val recordsWithNotNullCached = recordsWithCachedData
            .entries
            .mapNotNull { (deepRecord, cachedTrack) ->
                if (cachedTrack != null) deepRecord to cachedTrack else null
            }
            .toMap()

        val recordsWithMissing = recordsWithCachedData
            .filter { (_, cachedTrack) ->
                cachedTrack == null
            }
            .map { (deepRecord, _) -> deepRecord }

        val chunkedMissing = recordsWithMissing.chunked(50)

        println("Starting fetching more data")

        val recordsFromNetwork = chunkedMissing
            .flatMapIndexed { index, recordsWindow ->
                val ids = recordsWindow.map { it.base62Uri }
                val repoTracks = deps.trackRepo.getTracksBySpotifyIds(ids)
                val groupResult = recordsWindow.groupTracks(repoTracks)
                println("Chunk $index processed")
                groupResult
            }
            .toMap()

        recordsWithNotNullCached.forEach { (record, track) ->
            println("${track.artists.joinToString { it.name }} - ${track.title} played at ${record.ts}")
        }
        println("----------")
        recordsFromNetwork.forEach { (record, track) ->
            println("${track.artists.joinToString { it.name }} - ${track.title} played at ${record.ts}")
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

private fun List<DeepHistoryRecord>.groupTracks(repoResult: MultiTrackResult): List<Pair<DeepHistoryRecord, Track>> {
    return when (repoResult) {
        is MultiTrackResult.Success -> {
            this.map { indRecord ->
                val matchingTrack = repoResult.tracks.find { it.spotifyId == indRecord.base62Uri }!!
                indRecord to matchingTrack
            }
        }
        is MultiTrackResult.Mixed -> {
            this.mapNotNull { indRecord ->
                val matchingTrack = repoResult.tracks.find { it.spotifyId == indRecord.base62Uri }
                if (matchingTrack != null) {
                    indRecord to matchingTrack
                } else {
                    check(repoResult.missingIds.contains(indRecord.base62Uri))
                    null
                }
            }
        }
        is MultiTrackResult.Failure -> throw repoResult.exception
    }
}
