plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.time)
  api(libs.coroutines)
  api(libs.eithernet.core)
  api(libs.kotlinx.serialization.runtime.core)
  api(libs.metro.runtime)
  api(libs.okhttp)
  api(libs.retrofit)

  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotlin.test.junit)
}
