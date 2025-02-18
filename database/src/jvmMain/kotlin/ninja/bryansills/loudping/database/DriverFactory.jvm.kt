package ninja.bryansills.loudping.database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

actual class DriverFactory(private val url: String = JdbcSqliteDriver.IN_MEMORY) {
  actual fun createDriver(): SqlDriver {
    return JdbcSqliteDriver(
        url = url,
        schema = Database.Schema.synchronous(),
        migrateEmptySchema = true,
        properties = Properties().apply { put("foreign_keys", "true") },
    )
  }
}
