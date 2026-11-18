plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(libs.metro.runtime)
  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotlin.test.junit)
}
