package ninja.bryansills.loudping.deephistory

import ninja.bryansills.loudping.database.model.Track

sealed interface DeepHistoryRunEvent {
    data class EntriesLoaded(val playCount: Int) : DeepHistoryRunEvent

    data class CachedChunk(
        val found: List<Pair<DeepHistoryRecord, Track>>,
        val missing: List<DeepHistoryRecord>,
    ) : DeepHistoryRunEvent

    data class NetworkChunk(
        val found: List<Pair<DeepHistoryRecord, Track>>,
        val stillMissing: List<DeepHistoryRecord>,
    ) : DeepHistoryRunEvent
}
