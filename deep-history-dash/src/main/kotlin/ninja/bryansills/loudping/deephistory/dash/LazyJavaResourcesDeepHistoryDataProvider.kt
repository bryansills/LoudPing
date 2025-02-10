package ninja.bryansills.loudping.deephistory.dash

import kotlinx.serialization.json.Json
import ninja.bryansills.loudping.deephistory.DeepHistoryDataProvider
import ninja.bryansills.loudping.deephistory.DeepHistoryRecord
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

class LazyJavaResourcesDeepHistoryDataProvider(
    private val fileSystem: FileSystem = FileSystem.RESOURCES,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : DeepHistoryDataProvider {
    override val data: List<DeepHistoryRecord> by lazy {
        val jsonPaths = fileSystem.list(".".toPath())
            .filter { path ->
                "json" == path.name.split(".").lastOrNull()?.lowercase()
            }

        val jsonText = jsonPaths
            .map { path ->
                fileSystem.source(path).use { fileSource ->
                    fileSource.buffer().readUtf8()
                }
            }

        jsonText.flatMap { text ->
            json.decodeFromString<List<DeepHistoryRecord>>(text)
        }
    }
}
