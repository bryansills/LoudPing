package ninja.bryansills.loudping.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ninja.bryansills.loudping.database.Database
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.DriverFactory
import ninja.bryansills.loudping.database.RealDatabaseService
import ninja.bryansills.loudping.database.createDatabase

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {
  companion object {
    @Singleton
    @Provides
    fun provideDatabaseService(
        database: Database,
    ): DatabaseService {
      return RealDatabaseService(database = database)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
      val driverFactory = DriverFactory(context)
      return createDatabase(driverFactory)
    }
  }
}
