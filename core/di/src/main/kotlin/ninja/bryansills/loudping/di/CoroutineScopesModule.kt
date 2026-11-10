package ninja.bryansills.loudping.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@ContributesTo(AppScope::class)
@BindingContainer
object CoroutineScopesModule {

  @Provides
  @Dispatcher(LoudPingDispatcher.Io)
  fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Dispatcher(LoudPingDispatcher.Default)
  fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @SingleIn(AppScope::class)
  @ApplicationScope
  fun providesCoroutineScope(
    @Dispatcher(LoudPingDispatcher.Default) dispatcher: CoroutineDispatcher
  ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
