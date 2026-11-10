plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.core.di)
  api(projects.storage)
  api(libs.coroutines)
  api(libs.metro.runtime)
  implementation(libs.datastore.preferences.core)
}
