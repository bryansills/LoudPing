plugins {
  alias(libs.plugins.loudping.compose.screen)
}

dependencies {
  api(projects.networkAuth)
  api(projects.core.di)
  api(libs.androidx.navigation.common)
  api(libs.androidx.viewmodel.core)
  api(libs.compose.foundation.layout)
  api(libs.compose.runtime)
  api(libs.compose.ui.text)
  api(libs.coroutines)

  implementation(projects.ui.destinations)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.unit)
  implementation(libs.metro.runtime)
  implementation(libs.metro.viewmodel)
  implementation(libs.metro.viewmodel.compose)
}
