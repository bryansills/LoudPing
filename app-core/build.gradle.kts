plugins {
  alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
  api(libs.androidx.activity)
  api(libs.compose.animation)
  api(libs.compose.runtime)
  api(libs.compose.runtime.saveable)
  api(libs.androidx.viewmodel.core)
  api(libs.androidx.navigation.common)
  api(libs.androidx.navigation.runtime)
  api(libs.hilt.core)
  implementation(projects.appTheme)
  implementation(projects.ui.home)
  implementation(projects.ui.destinations)
  implementation(projects.ui.login)
  implementation(projects.ui.settings)
  implementation(projects.ui.playedTracks)
  implementation(projects.ui.refreshTokenEntry)

  implementation(libs.androidx.annotation)
  implementation(libs.androidx.activity.compose)
  implementation(libs.compose.ui)
  implementation(libs.androidx.core)
}
