plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.time)
  api(libs.coroutines)
  api(libs.retrofit)
  api(libs.okhttp)
  api(libs.eithernet.core)
  api(libs.kotlinx.serialization.runtime.core)
  implementation(libs.metro.runtime)

  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotlin.test.junit)
}
