package ninja.bryansills.loudping.database.android

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ninja.bryansills.loudping.database.Database

@Suppress("ktlint:standard:function-naming")
fun AndroidSqlDriver(context: Context): SqlDriver = AndroidSqliteDriver(Database.Schema.synchronous(), context, "loud-ping.db")
