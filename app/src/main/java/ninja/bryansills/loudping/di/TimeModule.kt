package ninja.bryansills.loudping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ninja.bryansills.loudping.time.RealTimeProvider
import ninja.bryansills.loudping.time.TimeProvider

@InstallIn(SingletonComponent::class)
@Module
interface TimeModule {
  companion object {
    @Singleton
    @Provides
    fun provideTimeProvider(): TimeProvider {
      return RealTimeProvider()
    }
  }
}
