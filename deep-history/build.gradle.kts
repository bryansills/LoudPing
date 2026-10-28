plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  api(projects.core.model)
  api(libs.kotlinx.datetime)
  implementation(projects.database.core)
  implementation(projects.trackRepo)
  implementation(libs.kotlinx.serialization.runtime.json)
}
