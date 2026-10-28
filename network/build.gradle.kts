plugins {
    alias(libs.plugins.loudping.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  implementation(projects.networkAuth)
  implementation(projects.sneakNetwork)
  implementation(projects.time)

  implementation(libs.coroutines)
  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx.serialization)
  implementation(libs.kotlinx.serialization.runtime.json)
  implementation(libs.okhttp)
  implementation(libs.kotlinx.datetime)
  api(libs.eithernet.core)

  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotlin.test.junit)
}
