plugins {
  alias(libs.plugins.loudping.jvm)
  application
}

application {
  mainClass = "ninja.bryansills.loudping.jvm.network.runner.MainKt"
}

dependencies {
  implementation(projects.core.coroutinesExt)
  implementation(projects.core.sneak)
  implementation(projects.database.core)
  implementation(projects.database.jvm)
  implementation(projects.historyRecorder)
  implementation(projects.network)
  implementation(projects.networkAuth)
  implementation(projects.session)
  implementation(projects.storage)
  implementation(projects.time)

  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx)
  implementation(libs.eithernet.retrofit)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp)
  implementation(libs.datastore.core)
  implementation(libs.datastore.preferences.core)
  implementation(libs.sqldelight.runtime)
  implementation(libs.coroutines)
  implementation(libs.kotlinx.datetime)
  implementation(libs.kotlinx.serialization.runtime.core)
}
