plugins { id("ninja.bryansills.multiplatform.library") }

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.datetime)
      implementation(projects.database)
      implementation(projects.network)
    }
  }
}
