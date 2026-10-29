plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  api(projects.storage)
  api(projects.time)
  api(projects.sneakNetwork)
  api(libs.coroutines)
  api(libs.retrofit)
  api(libs.okhttp)
  api(libs.eithernet.core)
  api(libs.kotlinx.serialization.runtime.core)
  implementation(projects.session)
  implementation(libs.datastore.preferences.core)
}
