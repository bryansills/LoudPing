plugins {
  id("ninja.bryansills.multiplatform.library")
  kotlin("plugin.serialization")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.storage)
      implementation(projects.time)
      implementation(projects.sneakNetwork)
      implementation(projects.session)

      implementation(libs.retrofit)
      implementation(libs.retrofit.kotlinx.serialization)
      implementation(libs.kotlinx.serialization.runtime)
      implementation(libs.okhttp)
      implementation(libs.eithernet)
    }
  }
}
