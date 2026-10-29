plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.core.model)
  api(libs.turbine)
  api(libs.coroutines)
  implementation(libs.coroutines.test)
}
