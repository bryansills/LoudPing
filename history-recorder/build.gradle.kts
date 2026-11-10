plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.database.core)
  api(projects.network)
  implementation(projects.core.model)
  implementation(libs.eithernet.core)
  implementation(libs.coroutines)
  implementation(libs.metro.runtime)
}
