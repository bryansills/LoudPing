plugins {
  alias(libs.plugins.loudping.compose.screen)
}

dependencies {
  api(libs.compose.animation)
  api(libs.compose.runtime)
  implementation(projects.ui.theme)
  implementation(projects.ui.home)
  implementation(projects.ui.destinations)
  implementation(projects.ui.login)
  implementation(projects.ui.settings)
  implementation(projects.ui.playedTracks)
  implementation(projects.ui.refreshTokenEntry)

  implementation(libs.androidx.core)
  implementation(libs.androidx.nav3.runtime)
  implementation(libs.androidx.nav3.ui)
  implementation(libs.androidx.viewmodel.nav3)
  implementation(libs.compose.ui)
}
