package ninja.bryansills.loudping.di.android

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ninja.bryansills.loudping.database.Database
import ninja.bryansills.loudping.database.DatabaseService
import ninja.bryansills.loudping.database.LoudPingDatabase
import ninja.bryansills.loudping.database.RealDatabaseService
import ninja.bryansills.loudping.database.android.AndroidSqlDriver

@ContributesTo(AppScope::class)
@BindingContainer
interface DatabaseModule {
  companion object {
    @SingleIn(AppScope::class)
    @Provides
    fun provideDatabaseService(database: Database): DatabaseService =
      RealDatabaseService(database = database)

    @Provides fun provideDatabase(sqlDriver: SqlDriver): Database = LoudPingDatabase(sqlDriver)

    @Provides
    fun provideSqlDriver(context: Context): SqlDriver =
      AndroidSqlDriver(context)
  }
}
