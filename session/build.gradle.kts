plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.storage)
  api(libs.coroutines)
  implementation(libs.datastore.preferences.core)
}
