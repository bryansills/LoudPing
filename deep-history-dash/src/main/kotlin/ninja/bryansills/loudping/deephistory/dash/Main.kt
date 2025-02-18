package ninja.bryansills.loudping.deephistory.dash

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jakewharton.mosaic.layout.background
import com.jakewharton.mosaic.modifier.Modifier
import com.jakewharton.mosaic.runMosaicBlocking
import com.jakewharton.mosaic.ui.Color
import com.jakewharton.mosaic.ui.Column
import com.jakewharton.mosaic.ui.Text
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import ninja.bryansills.loudping.deephistory.DeepHistoryRunEvent

fun main() = runMosaicBlocking {
    var runStats by remember { mutableStateOf(ProgressStats()) }
    LaunchedEffect(Unit) {
        val deps = initializeDependencies()
        runStats = runStats.copy(depsInitialized = true)
        deps.deepHistoryRunner(deps.deepHistoryDataProvider).flowOn(Dispatchers.Default).collect {
            event ->
            runStats =
                when (event) {
                    is DeepHistoryRunEvent.EntriesLoaded -> {
                        runStats.copy(recordCount = event.playCount)
                    }
                    is DeepHistoryRunEvent.CachedChunk -> {
                        runStats.copy(
                            cachedFound = runStats.cachedFound + event.found.size,
                            cachedMissing = runStats.cachedMissing + event.missing.size,
                        )
                    }
                    is DeepHistoryRunEvent.NetworkChunk -> {
                        runStats.copy(
                            networkFound = runStats.networkFound + event.found.size,
                            networkMissing = runStats.networkMissing + event.stillMissing.size,
                        )
                    }
                }
        }
    }

    Column(modifier = Modifier.background(Color.Black)) {
        Text(
            value = "Initialized: ${if (runStats.depsInitialized) "true" else "false"}",
            color = Color.White,
        )
        Text(value = "Total tracks: ${runStats.recordCount}", color = Color.White)
        Text(value = "Cached tracks: ${runStats.cachedFound}", color = Color.White)
        Text(value = "Non-cached tracks: ${runStats.cachedMissing}", color = Color.White)
        Text(value = "Network tracks: ${runStats.networkFound}", color = Color.White)
        Text(value = "Still missing tracks: ${runStats.networkMissing}", color = Color.White)
    }
}

data class ProgressStats(
    val depsInitialized: Boolean = false,
    val recordCount: Int = 0,
    val cachedFound: Int = 0,
    val cachedMissing: Int = 0,
    val networkFound: Int = 0,
    val networkMissing: Int = 0,
)
