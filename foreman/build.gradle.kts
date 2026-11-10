plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.historyRecorder)
  api(projects.storage)
  api(projects.time)
  api(projects.logger)
  api(projects.data.foremanMetro)
  api(libs.workmanager)
  api(libs.coroutines)
  implementation(libs.datastore.preferences.core)
  implementation(libs.metro.runtime)
}
