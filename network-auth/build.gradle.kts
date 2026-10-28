plugins {
    alias(libs.plugins.loudping.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  implementation(projects.storage)
  implementation(projects.time)
  implementation(projects.sneakNetwork)
  implementation(projects.session)

  implementation(libs.coroutines)
  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx.serialization)
  implementation(libs.kotlinx.serialization.runtime.json)
  implementation(libs.okhttp)
  implementation(libs.eithernet.core)
}
