package ninja.bryansills.loudping.core.test

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlinx.datetime.Instant

class TimestampTicker(
    initialTime: Instant = Instant.parse("2025-02-23T21:55:31Z"),
    private val tick: Duration = 2.minutes,
) {
    var currentTime = initialTime

    val next: Instant
        get() {
            currentTime += tick
            return currentTime
        }
}
