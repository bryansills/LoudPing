package ninja.bryansills.loudping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.history.recorder.HistoryRecorder
import ninja.bryansills.loudping.history.recorder.RealHistoryRecorder
import ninja.bryansills.loudping.network.NetworkService

@Module
@InstallIn(SingletonComponent::class)
interface HistoryRecorderModule {
  companion object {
    @Provides
    fun provideHistoryRecorder(
        networkService: NetworkService,
        databaseService: DatabaseService,
    ): HistoryRecorder {
      return RealHistoryRecorder(
          networkService = networkService,
          databaseService = databaseService,
      )
    }
  }
}
