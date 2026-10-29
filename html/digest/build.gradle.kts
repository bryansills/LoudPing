plugins {
  alias(libs.plugins.loudping.jvm)
  application
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.burst.plugin)
}

application {
  mainClass = "ninja.bryansills.loudping.html.digest.MainKt"
}

dependencies {
  implementation(projects.html.core)
  implementation(projects.time)
  implementation(libs.okhttp)
  implementation(libs.retrofit)
  implementation(libs.jaxb3Api)
  implementation(libs.htmlunit)
  implementation(libs.kotlinx.serialization.runtime.json)
  implementation(libs.kotlinx.html)
  implementation(libs.okio)
  implementation(libs.coroutines)
  implementation(libs.kotlinx.datetime)
  implementation(libs.kotlinx.serialization.runtime.core)
  runtimeOnly(libs.jaxb3Impl)
  testImplementation(libs.retrofit.jaxb3)
  testImplementation(libs.kotlin.test)
  testImplementation(libs.burst.coroutines)
  testImplementation(libs.kotlin.test.junit)
  testImplementation(libs.coroutines.test)
}
