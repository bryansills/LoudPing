package ninja.bryansills.loudping.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ninja.bryansills.loudping.database.Database
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.LoudPingDatabase
import ninja.bryansills.loudping.database.RealDatabaseService
import ninja.bryansills.loudping.database.android.AndroidSqlDriver

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {
  companion object {
    @Singleton
    @Provides
    fun provideDatabaseService(database: Database): DatabaseService =
      RealDatabaseService(database = database)

    @Provides fun provideDatabase(sqlDriver: SqlDriver): Database = LoudPingDatabase(sqlDriver)

    @Provides
    fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver =
      AndroidSqlDriver(context)
  }
}
