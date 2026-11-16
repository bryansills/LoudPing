plugins {
  alias(libs.plugins.loudping.compose.screen)
}

dependencies {
  api(projects.ui.destinations)
  api(libs.compose.animation)
  api(libs.compose.runtime)
  runtimeOnly(projects.ui.login)
  implementation(projects.ui.theme)
  implementation(projects.ui.home)
  implementation(projects.ui.settings)
  implementation(projects.ui.playedTracks)
  implementation(projects.ui.refreshTokenEntry)

  implementation(libs.androidx.core)
  implementation(libs.androidx.nav3.runtime)
  implementation(libs.androidx.nav3.ui)
  implementation(libs.androidx.viewmodel.core)
  implementation(libs.androidx.viewmodel.nav3)
  implementation(libs.compose.runtime.saveable)
  implementation(libs.compose.ui)
}
