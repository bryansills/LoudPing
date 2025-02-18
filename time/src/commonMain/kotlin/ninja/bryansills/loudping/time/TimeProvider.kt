package ninja.bryansills.loudping.time

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface TimeProvider {
  val now: Instant
}

class RealTimeProvider : TimeProvider {
  override val now: Instant
    get() = Clock.System.now()
}
