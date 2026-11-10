plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  api(projects.network)
  api(projects.networkAuth)
  api(projects.sneakNetwork)
  implementation(projects.core.model)
  implementation(libs.eithernet.core)
  implementation(libs.eithernet.retrofit)
  implementation(libs.coroutines)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.metro.runtime)
  implementation(libs.okhttp)
  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx)
}
