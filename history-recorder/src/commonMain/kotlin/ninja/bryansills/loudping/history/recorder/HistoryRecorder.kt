package ninja.bryansills.loudping.history.recorder

import kotlinx.datetime.Instant

interface HistoryRecorder {
    /**
     * @param startAt What time to start recording history (often the current time).
     * @param stopAt What (earlier) time to stop recording history. If null, keep recording history
     * until Spotify says you have reached the end.
     */
    suspend fun invoke(startAt: Instant, stopAt: Instant?): Result<Instant>
}
