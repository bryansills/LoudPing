plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.core.model)
  api(projects.database.core)
  api(projects.network)
  implementation(libs.eithernet.core)
}
