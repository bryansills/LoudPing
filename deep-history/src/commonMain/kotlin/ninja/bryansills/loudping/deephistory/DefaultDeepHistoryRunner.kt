package ninja.bryansills.loudping.deephistory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ninja.bryansills.loudping.database.model.Track
import ninja.bryansills.loudping.repository.track.MultiTrackResult
import ninja.bryansills.loudping.repository.track.TrackRepository

class DefaultDeepHistoryRunner(
    private val trackRepository: TrackRepository
) : DeepHistoryRunner {
    override fun invoke(dataProvider: DeepHistoryDataProvider): Flow<DeepHistoryRunEvent> = flow {
        val records = dataProvider.data
        emit(DeepHistoryRunEvent.EntriesLoaded(records.size))

        val chunkedRecords = records.chunked(100)

        val recordsWithCachedTracks = mutableListOf<Pair<DeepHistoryRecord, Track>>()
        val recordsWithoutCachedTracks = mutableListOf<DeepHistoryRecord>()

        chunkedRecords.forEach { chunk ->
            val processedChunk = chunk.associateWith { deepRecord ->
                val cachedTrack = trackRepository.getTrackBySpotifyId(deepRecord.base62Uri)
                cachedTrack
            }

            val processedCached = processedChunk
                .entries
                .mapNotNull { (deepRecord, cachedTrack) ->
                    if (cachedTrack != null) deepRecord to cachedTrack else null
                }

            val processedMissing = processedChunk
                .filter { (_, cachedTrack) ->
                    cachedTrack == null
                }
                .map { (deepRecord, _) -> deepRecord }

            emit(
                DeepHistoryRunEvent.CachedChunk(
                    found = processedCached,
                    missing = processedMissing
                )
            )

            recordsWithCachedTracks.addAll(processedCached)
            recordsWithoutCachedTracks.addAll(processedMissing)
        }

        val chunkedMissing = recordsWithoutCachedTracks.chunked(50)

        chunkedMissing.forEach { recordsWindow ->
            val ids = recordsWindow.map { it.base62Uri }
            val repoTracks = trackRepository.getTracksBySpotifyIds(ids)
            val groupResult = recordsWindow.groupRecords(repoTracks)

            emit(
                DeepHistoryRunEvent.NetworkChunk(
                    found = groupResult.found,
                    stillMissing = groupResult.stillMissing
                )
            )
        }
    }
}

private fun List<DeepHistoryRecord>.groupRecords(repoResult: MultiTrackResult): NetworkGroupedRecords {
    return when (repoResult) {
        is MultiTrackResult.Success -> {
            val found = this.map { indRecord ->
                val matchingTrack = repoResult.tracks.find { it.spotifyId == indRecord.base62Uri }!!
                indRecord to matchingTrack
            }

            NetworkGroupedRecords(
                found = found,
                stillMissing = listOf()
            )
        }
        is MultiTrackResult.Mixed -> {
            val found = this.mapNotNull { indRecord ->
                val matchingTrack = repoResult.tracks.find { it.spotifyId == indRecord.base62Uri }
                if (matchingTrack != null) {
                    indRecord to matchingTrack
                } else {
                    null
                }
            }
            val stillMissing = this.mapNotNull { indRecord ->
                val matchingTrack = repoResult.tracks.find { it.spotifyId == indRecord.base62Uri }
                if (matchingTrack != null) {
                    null
                } else {
                    check(repoResult.missingIds.contains(indRecord.base62Uri))
                    indRecord
                }
            }

            NetworkGroupedRecords(
                found = found,
                stillMissing = stillMissing
            )
        }
        is MultiTrackResult.Failure -> throw repoResult.exception
    }
}

data class NetworkGroupedRecords(
    val found: List<Pair<DeepHistoryRecord, Track>>,
    val stillMissing: List<DeepHistoryRecord>
)

