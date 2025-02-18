package ninja.bryansills.loudping.database

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import migrations.Track_play_record

expect class DriverFactory {
  fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
  val driver = driverFactory.createDriver()
  return Database(
      driver = driver,
      track_play_recordAdapter =
          Track_play_record.Adapter(
              contextAdapter = EnumColumnAdapter(),
          ),
  )
}
