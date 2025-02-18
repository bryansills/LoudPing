package ninja.bryansills.loudping.history.recorder

import kotlinx.datetime.Instant

sealed interface TrackHistoryResult {
  data class Standard(
      val oldestTimestamp: Instant,
      val newestTimestamp: Instant,
  ) : TrackHistoryResult

  data object NothingPlayed : TrackHistoryResult
}
