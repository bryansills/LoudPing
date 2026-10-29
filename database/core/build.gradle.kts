plugins {
  alias(libs.plugins.loudping.sqldelight)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.core.model)
      api(libs.coroutines)
      api(libs.kotlinx.datetime)
      api(libs.paging)
    }
  }
}

sqldelight {
  databases {
    create("Database") {
      packageName = "ninja.bryansills.loudping.database"
      schemaOutputDirectory = file("src/main/sqldelight/databases")
      verifyMigrations = true
      generateAsync = true
      deriveSchemaFromMigrations = true
      dialect(libs.sqldelight.sqlite.dialect)
    }
  }
}
