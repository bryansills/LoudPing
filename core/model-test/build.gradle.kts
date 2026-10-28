plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.core.model)
  implementation(libs.turbine)
  implementation(libs.coroutines.test)
}
