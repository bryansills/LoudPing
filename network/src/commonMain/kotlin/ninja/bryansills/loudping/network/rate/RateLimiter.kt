package ninja.bryansills.loudping.network.rate

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.time.TimeProvider

class RateLimiter(
    private val timeProvider: TimeProvider,
    private val sleeper: Sleeper,
    @Volatile var permitsPerWindow: Long = 1L,
    private val windowSize: Duration = 1.seconds
) {
    private val allocatedUntil = MutableStateFlow(timeProvider.now)

    fun tryAcquire(permitsRequested: Long, timeout: Duration): Boolean {
        require(permitsRequested > 0) { "unexpected permitCount: $permitsRequested" }
        require(!timeout.isNegative()) { "unexpected timeout: $timeout" }

        val sleepTime = timeToAcquire(permitsRequested, timeout)
            ?: return false

        sleeper.sleep(sleepTime)

        return true
    }

    fun blockUntil(rateLimitEnd: Instant) {
        allocatedUntil.value = rateLimitEnd
    }

    private fun timeToAcquire(permitsRequested: Long, timeout: Duration): Duration? {
        while (true) {
            val currentlyAllocatedUntil = allocatedUntil.value
            val permitsPerWindow = this.permitsPerWindow

            if (permitsRequested > permitsPerWindow) return null

            val now = timeProvider.now
            val permitRequestCost = calculatePermitCost(permitsRequested, windowSize, permitsPerWindow)
            val newAllocatedUntil = maxOf(currentlyAllocatedUntil, now) + permitRequestCost
            val gottaSleepUntil = currentlyAllocatedUntil - now

            if (gottaSleepUntil > timeout) return null

            if (!allocatedUntil.compareAndSet(currentlyAllocatedUntil, newAllocatedUntil)) continue

            return gottaSleepUntil.takeIf { it.isPositive() } ?: Duration.ZERO
        }
    }

    private fun calculatePermitCost(permitsRequested: Long, windowSize: Duration, permitsPerWindow: Long): Duration {
        val nanosPerPermit = windowSize.inWholeNanoseconds / permitsPerWindow
        val nanosForPermitsRequested = permitsRequested * nanosPerPermit
        val result = nanosForPermitsRequested.toDuration(DurationUnit.NANOSECONDS)
        return result
    }
}

interface Sleeper {
    /**
     * Sleeps if @param duration is a positive amount
     */
    fun sleep(duration: Duration)

    companion object {
        val Default: Sleeper = object : Sleeper {
            override fun sleep(duration: Duration) {
                if (duration.isPositive()) {
                    Thread.sleep(duration.toLong(DurationUnit.MILLISECONDS))
                }
            }
        }
    }
}
