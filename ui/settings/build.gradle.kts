plugins {
  alias(libs.plugins.loudping.compose.screen)
}

dependencies {
  api(projects.networkAuth)
  api(projects.foreman)
  api(libs.androidx.viewmodel.core)
  api(libs.compose.runtime)
  api(libs.compose.ui)
  api(libs.coroutines)
  api(libs.workmanager)

  implementation(projects.ui.theme)
  implementation(projects.ui.androidAppRes)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.compose.foundation)
  implementation(libs.compose.foundation.layout)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.text)
  implementation(libs.compose.ui.unit)
  implementation(libs.kotlinx.datetime)
  implementation(libs.metro.runtime)
  implementation(libs.metro.viewmodel)
  implementation(libs.metro.viewmodel.compose)
}
