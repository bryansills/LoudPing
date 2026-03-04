package ninja.bryansills.loudping.time

import kotlin.time.Clock
import kotlin.time.Instant

interface TimeProvider {
    val now: Instant
}

class RealTimeProvider : TimeProvider {
    override val now: Instant
        get() = Clock.System.now()
}
