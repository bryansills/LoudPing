plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.sneak)
  implementation(libs.metro.runtime)
}
