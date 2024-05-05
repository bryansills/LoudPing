package ninja.bryansills.loudping.database

import app.cash.sqldelight.db.SqlDriver
import ninja.bryansills.louping.database.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}
