plugins {
  alias(libs.plugins.loudping.compose.screen)
}

dependencies {
  api(projects.networkAuth)
  api(projects.core.di)
  api(libs.androidx.viewmodel.core)
  api(libs.compose.foundation.layout)
  api(libs.compose.runtime)
  api(libs.compose.ui.text)
  api(libs.coroutines)
  api(libs.metro.runtime)
  api(libs.metro.viewmodel)

  implementation(projects.ui.destinations)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.nav3.runtime)
  implementation(libs.androidx.nav3.ui)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.unit)
  implementation(libs.metro.viewmodel.compose)
}
