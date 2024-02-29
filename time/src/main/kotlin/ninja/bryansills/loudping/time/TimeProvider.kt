package ninja.bryansills.loudping.time

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface TimeProvider {
    val now: Instant
}

class RealTimeProvider @Inject constructor() : TimeProvider {
    override val now: Instant
        get() = Clock.System.now()
}

@InstallIn(SingletonComponent::class)
@Module
interface TimeModule {
    @Binds
    fun bindTimeProvider(realTimeProvider: RealTimeProvider): TimeProvider
}
