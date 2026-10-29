plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.albumGrouper)
  api(projects.albumRepo)
}
