package ninja.bryansills.loudping.history.recorder

import kotlinx.datetime.Instant

sealed interface AlbumBatcherResult {
  /**
   * @param mostRecentRecord The timestamp indicating the completion of the playback of the most
   *   recent album played.
   */
  data class Standard(val mostRecentRecord: Instant) : AlbumBatcherResult

  data object NothingRecorded : AlbumBatcherResult
}
