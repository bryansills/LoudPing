plugins {
  alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
  api(projects.networkAuth)
  api(projects.foreman)
  api(libs.compose.runtime)
  api(libs.compose.ui)
  api(libs.androidx.viewmodel.core)
  api(libs.workmanager)
  api(libs.dagger)
  implementation(libs.hilt.android)
  api(libs.javax.inject)
  api(libs.coroutines)

  implementation(projects.appTheme)
  implementation(projects.androidAppRes)
  implementation(libs.kotlinx.datetime)
  implementation(libs.compose.foundation)
  implementation(libs.compose.foundation.layout)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.text)
  implementation(libs.compose.ui.unit)
  implementation(libs.hilt.viewmodel.compose)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.hilt.navigation.compose)
  implementation(libs.hilt.core)
  implementation(libs.compose.material3)
}
