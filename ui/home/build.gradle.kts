plugins {
  alias(libs.plugins.loudping.compose.screen)
}

dependencies {
  api(projects.session)
  api(projects.foreman)
  api(libs.androidx.viewmodel.core)
  api(libs.compose.foundation.layout)
  api(libs.compose.runtime)
  api(libs.compose.ui)
  api(libs.coroutines)
  api(libs.metro.runtime)
  api(libs.metro.viewmodel)

  implementation(projects.ui.theme)
  implementation(projects.ui.androidAppRes)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.compose.foundation)
  implementation(libs.compose.icons)
  implementation(libs.compose.material3)
  implementation(libs.compose.preview)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.text)
  implementation(libs.compose.ui.tooling)
  implementation(libs.compose.ui.unit)
  implementation(libs.metro.viewmodel.compose)
}
