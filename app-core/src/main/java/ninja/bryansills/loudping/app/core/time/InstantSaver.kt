package ninja.bryansills.loudping.app.core.time

import androidx.compose.runtime.saveable.Saver
import kotlinx.datetime.Instant

val InstantSaver: Saver<Instant, String> =
    Saver(
        save = { it.toString() },
        restore = { Instant.parse(it) },
    )
