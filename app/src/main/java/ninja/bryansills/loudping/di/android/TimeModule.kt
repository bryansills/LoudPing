package ninja.bryansills.loudping.di.android

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ninja.bryansills.loudping.time.RealTimeProvider
import ninja.bryansills.loudping.time.TimeProvider

@ContributesTo(AppScope::class)
@BindingContainer
object TimeModule {
  @SingleIn(AppScope::class) @Provides fun provideTimeProvider(): TimeProvider = RealTimeProvider()
}
