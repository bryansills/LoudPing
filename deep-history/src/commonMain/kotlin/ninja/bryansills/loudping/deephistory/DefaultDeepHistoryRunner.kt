package ninja.bryansills.loudping.deephistory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ninja.bryansills.loudping.core.model.Track
import ninja.bryansills.loudping.core.model.TrackPlayContext
import ninja.bryansills.loudping.core.model.TrackPlayRecord
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.repository.track.MultiTrackResult
import ninja.bryansills.loudping.repository.track.TrackRepository

class DefaultDeepHistoryRunner(
    private val trackRepository: TrackRepository,
    private val databaseService: DatabaseService,
) : DeepHistoryRunner {
    override fun invoke(dataProvider: DeepHistoryDataProvider): Flow<DeepHistoryRunEvent> = flow {
        val records = dataProvider.data
        emit(DeepHistoryRunEvent.EntriesLoaded(records.size))

        val chunkedRecords = records.chunked(100)

        val completeRecords = mutableListOf<Pair<DeepHistoryRecord, Track>>()
        val stillTryingRecords = mutableListOf<DeepHistoryRecord>()

        chunkedRecords.forEach { recordsWindow ->
            val repoTracks = trackRepository.getTracksBySpotifyIds(
                trackIds = recordsWindow.map { it.base62Uri },
                shouldQueryNetworkForMissing = false,
            )
            val groupResult = recordsWindow.groupRecords(repoTracks)

            emit(
                DeepHistoryRunEvent.CachedChunk(
                    found = groupResult.found,
                    missing = groupResult.stillMissing,
                ),
            )

            completeRecords.addAll(groupResult.found)
            stillTryingRecords.addAll(groupResult.stillMissing)
        }

        val chunkedMissing = stillTryingRecords.chunked(50)
        val failedRecords = mutableListOf<DeepHistoryRecord>()

        chunkedMissing.forEach { recordsWindow ->
            val repoTracks = trackRepository.getTracksBySpotifyIds(
                trackIds = recordsWindow.map { it.base62Uri },
                shouldQueryNetworkForMissing = true,
            )
            val groupResult = recordsWindow.groupRecords(repoTracks)

            emit(
                DeepHistoryRunEvent.NetworkChunk(
                    found = groupResult.found,
                    stillMissing = groupResult.stillMissing,
                ),
            )

            completeRecords.addAll(groupResult.found)
            failedRecords.addAll(groupResult.stillMissing)
        }

        val trackPlayRecordChunks = completeRecords
            .map { (deepHistoryRecord, track) ->
                TrackPlayRecord(
                    track = track,
                    timestamp = deepHistoryRecord.ts,
                    context = TrackPlayContext.Unknown,
                )
            }
            .sortedBy { it.timestamp }
            .chunked(100)

        trackPlayRecordChunks.forEach { chunk ->
            databaseService.insertTrackPlayRecords(records = chunk)
            emit(DeepHistoryRunEvent.RecordedChunk(recorded = chunk))
        }
    }
}

private fun List<DeepHistoryRecord>.groupRecords(repoResult: MultiTrackResult): NetworkGroupedRecords = when (repoResult) {
    is MultiTrackResult.Success -> {
        val found = this.map { indRecord ->
            val matchingTrack = repoResult.tracks.find { it.spotifyId == indRecord.base62Uri }!!
            indRecord to matchingTrack
        }

        NetworkGroupedRecords(
            found = found,
            stillMissing = listOf(),
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
            stillMissing = stillMissing,
        )
    }

    is MultiTrackResult.Failure -> throw repoResult.exception
}

data class NetworkGroupedRecords(
    val found: List<Pair<DeepHistoryRecord, Track>>,
    val stillMissing: List<DeepHistoryRecord>,
)
