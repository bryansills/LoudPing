plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.database.core)
  api(projects.network)
  implementation(projects.core.model)
  implementation(libs.eithernet.core)
  implementation(libs.coroutines)
}
