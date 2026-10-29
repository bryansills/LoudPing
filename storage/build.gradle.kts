plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(libs.coroutines)
  api(libs.datastore.core)
  api(libs.datastore.preferences.core)
}
