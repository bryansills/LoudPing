package ninja.bryansills.loudping.database.android

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ninja.bryansills.loudping.database.Database

fun AndroidSqlDriver(context: Context): SqlDriver {
    return AndroidSqliteDriver(Database.Schema.synchronous(), context, "loud-ping.db")
}
