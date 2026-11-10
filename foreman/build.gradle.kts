plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.data.foremanMetro)
  api(projects.historyRecorder)
  api(projects.logger)
  api(projects.storage)
  api(projects.time)
  api(libs.coroutines)
  api(libs.metro.runtime)
  api(libs.workmanager)
  implementation(libs.datastore.preferences.core)
}
