package ninja.bryansills.loudping.database.jvm

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

fun JvmSqlDriver(url: String = JdbcSqliteDriver.IN_MEMORY): SqlDriver = JdbcSqliteDriver(
    url = url,
    schema = Database.Schema.synchronous(),
    migrateEmptySchema = true,
    properties = Properties().apply { put("foreign_keys", "true") },
)
