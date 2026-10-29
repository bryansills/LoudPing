plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  api(projects.core.model)
  api(projects.database.core)
  api(projects.trackRepo)
  api(libs.coroutines)
  api(libs.kotlinx.serialization.runtime.core)
}
