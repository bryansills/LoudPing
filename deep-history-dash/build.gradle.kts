plugins {
  alias(libs.plugins.loudping.mosaic)
}

application {
  mainClass = "ninja.bryansills.loudping.deephistory.dash.MainKt"
}

dependencies {
  implementation(projects.core.sneak)
  implementation(projects.deepHistory)
  implementation(projects.database.core)
  implementation(projects.database.jvm)
  implementation(projects.network)
  implementation(projects.networkAuth)
  implementation(projects.session)
  implementation(projects.storage)
  implementation(projects.time)
  implementation(projects.trackRepo)

  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx)
  implementation(libs.eithernet.retrofit)
  implementation(libs.okhttp)
  implementation(libs.okhttp.logging)
  implementation(libs.okio)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.datastore.core)
  implementation(libs.datastore.preferences.core)
  implementation(libs.sqldelight.runtime)
  implementation(libs.compose.desktop.runtime)
  implementation(libs.coroutines)
  implementation(libs.kotlinx.datetime)
  implementation(libs.kotlinx.serialization.runtime.core)
}
