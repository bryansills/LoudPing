package ninja.bryansills.loudping.di

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
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
import ninja.bryansills.loudping.database.LoudPingDatabase
import ninja.bryansills.loudping.database.android.AndroidSqlDriver

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {
    companion object {
        @Singleton
        @Provides
        fun provideDatabaseService(
            database: Database,
        ): DatabaseService = RealDatabaseService(database = database)

        @Provides
        fun provideDatabase(sqlDriver: SqlDriver): Database {
            return LoudPingDatabase(sqlDriver)
        }

        @Provides
        fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
            return AndroidSqlDriver(context)
        }
    }
}
