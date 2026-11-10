plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  api(projects.network)
  api(projects.networkAuth)
  api(projects.sneakNetwork)
  api(libs.kotlinx.serialization.json)
  api(libs.metro.runtime)
  api(libs.okhttp)
  implementation(libs.eithernet.retrofit)
  implementation(libs.kotlinx.serialization.runtime.core)
  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx)
}
