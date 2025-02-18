package ninja.bryansills.loudping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ninja.bryansills.loudping.logger.Logger
import ninja.bryansills.loudping.logger.bugsnag.BugsnagLogger

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {
  companion object {
    @Singleton
    @Provides
    fun provideLogger(): Logger {
      return BugsnagLogger()
    }
  }
}
