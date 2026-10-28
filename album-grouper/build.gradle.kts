plugins {
  alias(libs.plugins.loudping.jvm)
}

dependencies {
  api(projects.core.model)
  implementation(libs.coroutines)
  testImplementation(projects.core.modelTest)
  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotlin.test.junit)
  testImplementation(libs.turbine)
}
