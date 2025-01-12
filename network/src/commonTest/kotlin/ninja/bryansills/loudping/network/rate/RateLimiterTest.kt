package ninja.bryansills.loudping.network.rate

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlinx.datetime.Instant
import ninja.bryansills.loudping.time.TimeProvider

class RateLimiterTest {
    @Test
    fun basic() {
        val timestamps = instantsOf(
            "2025-01-10T05:21:12Z", // used when initializing the RateLimiter
            "2025-01-10T05:21:13Z" // used when getting the first permit
        )
        val timeProvider = ScriptedTimeProvider(timestamps)
        val sleeper = FakeSleeper()

        val rateLimiter = RateLimiter(
            timeProvider = timeProvider,
            sleeper = sleeper,
            permitsPerWindow = 1,
            windowSize = 1.minutes
        )

        val permit = rateLimiter.tryAcquire(permitsRequested = 1, timeout = 1.hours)

        // expected:
        // - we got the permit
        // - we did not have to wait to get the permit
        assertEquals(true, permit)
        assertEquals(Duration.ZERO, sleeper.sleeps[0])
    }

    @Test
    fun `sleeps if you need to wait for another permit`() {
        val timestamps = instantsOf(
            "2025-01-10T05:21:12Z", // used when initializing the RateLimiter
            "2025-01-10T05:21:13Z", // used when getting the first permit
            "2025-01-10T05:21:14Z" // used when getting the second permit
        )
        val timeProvider = ScriptedTimeProvider(timestamps)
        val sleeper = FakeSleeper()

        val rateLimiter = RateLimiter(
            timeProvider = timeProvider,
            sleeper = sleeper,
            permitsPerWindow = 1,
            windowSize = 1.minutes
        )

        val firstPermit = rateLimiter.tryAcquire(permitsRequested = 1, timeout = 1.hours)
        val secondPermit = rateLimiter.tryAcquire(permitsRequested = 1, timeout = 1.hours)

        // expected:
        // - we got both permits
        // - we did not have to wait to get the first permit, but had to wait until the next window
        //   (the following minute) to get the second permit
        assertEquals(true, firstPermit)
        assertEquals(Duration.ZERO, sleeper.sleeps[0])
        assertEquals(true, secondPermit)
        assertEquals(59.seconds, sleeper.sleeps[1])
    }

    @Test
    fun `allows you to have many permits per window`() {
        val timestamps = instantsOf(
            "2025-01-10T05:21:12Z", // used when initializing the RateLimiter
            "2025-01-10T05:21:13Z", // used when getting the 5 permits
        )
        val timeProvider = ScriptedTimeProvider(timestamps)
        val sleeper = FakeSleeper()

        val rateLimiter = RateLimiter(
            timeProvider = timeProvider,
            sleeper = sleeper,
            permitsPerWindow = 5,
            windowSize = 1.minutes
        )

        val firstPermit = rateLimiter.tryAcquire(permitsRequested = 1, timeout = 1.hours)

        // expected:
        // - we got 5 permits
        // - we did not have to wait to get the permits
        assertEquals(true, firstPermit)
        assertEquals(Duration.ZERO, sleeper.sleeps[0])
    }

    @Test
    fun `allows you to request many permits throughout a single window`() {
        val timeBoi = SingleThreadTimeBoi("2025-01-10T05:21:12Z")

        val rateLimiter = RateLimiter(
            timeProvider = timeBoi,
            sleeper = timeBoi,
            permitsPerWindow = 5,
            windowSize = 1.minutes
        )

        val permits = (1..5).map { rateLimiter.tryAcquire(permitsRequested = 1, timeout = 1.hours) }

        // expected:
        // - we got 5 permits
        // - we got all 5 permits within the window
        // undefined behavior (aka you shouldn't depend on these):
        // - the permits being evenly spread out over that window
        // - the permits being bunched up at the beginning of that window
        assertEquals(true, permits.all { it })
        assertEquals(true, (timeBoi.current - timeBoi.start) < 1.minutes)
    }
}

private fun instantsOf(vararg timestamps: String): List<Instant> {
    return timestamps.map { Instant.parse(it) }
}

private class ScriptedTimeProvider(val timesToGive: List<Instant>) : TimeProvider {
    private var index = 0

    override val now: Instant
        get() = timesToGive[index++]
}

private class FakeSleeper : Sleeper {
    val sleeps = mutableListOf<Duration>()

    override fun sleep(duration: Duration) {
        sleeps.add(duration)
    }
}

private class SingleThreadTimeBoi(val start: Instant) : TimeProvider, Sleeper {
    var current: Instant = start

    override val now: Instant
        get() = current

    val allSleeps = mutableListOf<Duration>()

    constructor(startString: String) : this(Instant.parse(startString))

    override fun sleep(duration: Duration) {
        allSleeps.add(duration)
        current += duration
    }
}
