package ninja.bryansills.loudping.history.recorder

import kotlinx.datetime.Instant

interface AlbumBatcher {
    /**
     * @param startAt What time to start batching albums (often the current time).
     * @param stopAt What (earlier) time to stop batching albums. If null, keep batching albums
     *   until you have reached the end of the tracked playback.
     */
    suspend operator fun invoke(startAt: Instant, stopAt: Instant?): Result<AlbumBatcherResult>
}
