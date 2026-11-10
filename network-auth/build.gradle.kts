plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.sneakNetwork)
  api(projects.storage)
  api(projects.time)
  api(libs.coroutines)
  api(libs.eithernet.core)
  api(libs.kotlinx.serialization.runtime.core)
  api(libs.metro.runtime)
  api(libs.okhttp)
  api(libs.retrofit)
  implementation(projects.session)
  implementation(libs.datastore.preferences.core)
}
