plugins {
  alias(libs.plugins.loudping.android.library.kotlin.dagger)
}

dependencies {
  ksp(libs.hilt.compiler.androidx)
  api(projects.historyRecorder)
  api(projects.storage)
  api(projects.time)
  api(projects.logger)
  api(libs.workmanager.hilt)
  api(libs.workmanager)
  api(libs.dagger)
  api(libs.javax.inject)
  api(libs.coroutines)
  api(libs.hilt.android)
  implementation(libs.hilt.core)
  implementation(libs.datastore.preferences.core)
  implementation(libs.hilt.common)
}
