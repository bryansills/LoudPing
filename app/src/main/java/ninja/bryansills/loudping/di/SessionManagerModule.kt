package ninja.bryansills.loudping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import ninja.bryansills.loudping.session.RealSessionManager
import ninja.bryansills.loudping.session.SessionManager
import ninja.bryansills.loudping.storage.SimpleStorage

@Module
@InstallIn(SingletonComponent::class)
interface SessionManagerModule {
  companion object {
    @Singleton
    @Provides
    fun provideSessionManager(
        simpleStorage: SimpleStorage,
        @ApplicationScope coroutineScope: CoroutineScope,
    ): SessionManager {
      return RealSessionManager(
          simpleStorage = simpleStorage,
          coroutineScope = coroutineScope,
      )
    }
  }
}
