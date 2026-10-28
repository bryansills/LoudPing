plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.core.model)
  api(projects.albumGrouper)
  implementation(projects.albumRepo)
}
