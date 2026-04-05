package ninja.bryansills.loudping.database

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import migrations.Album
import migrations.Track_play_record

@Suppress("ktlint:standard:function-naming")
fun LoudPingDatabase(driver: SqlDriver): Database =
  Database(
    driver = driver,
    track_play_recordAdapter = Track_play_record.Adapter(contextAdapter = EnumColumnAdapter()),
    albumAdapter = Album.Adapter(typeAdapter = EnumColumnAdapter()),
  )
