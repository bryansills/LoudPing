package ninja.bryansills.loudping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineScopesModule {

  @Provides
  @Dispatcher(LoudPingDispatcher.Io)
  fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Dispatcher(LoudPingDispatcher.Default)
  fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @Singleton
  @ApplicationScope
  fun providesCoroutineScope(
      @Dispatcher(LoudPingDispatcher.Default) dispatcher: CoroutineDispatcher,
  ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
