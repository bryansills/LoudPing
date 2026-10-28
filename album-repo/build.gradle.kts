plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.core.model)
  implementation(projects.database.core)
  implementation(projects.network)
}
