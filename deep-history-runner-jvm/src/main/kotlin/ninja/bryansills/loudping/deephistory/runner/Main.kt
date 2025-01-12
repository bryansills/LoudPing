package ninja.bryansills.loudping.deephistory.runner

import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.deephistory.DeepHistoryRecord
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main() {
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

    val records = jsonText.flatMap { text ->
        json.decodeFromString<List<DeepHistoryRecord>>(text)
    }

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
