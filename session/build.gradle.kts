plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.storage)
  api(libs.coroutines)
  implementation(projects.core.di)
  implementation(libs.datastore.preferences.core)
  implementation(libs.metro.runtime)
}
