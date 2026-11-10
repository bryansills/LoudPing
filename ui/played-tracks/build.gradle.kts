plugins {
  alias(libs.plugins.loudping.compose.screen)
}

dependencies {
  api(projects.core.model)
  api(projects.database.core)
  api(libs.androidx.viewmodel.core)
  api(libs.compose.foundation)
  api(libs.compose.runtime)
  api(libs.compose.ui)
  api(libs.coroutines)
  api(libs.metro.runtime)
  api(libs.metro.viewmodel)

  implementation(projects.ui.theme)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.compose.foundation.layout)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.text)
  implementation(libs.compose.ui.unit)
  implementation(libs.metro.viewmodel.compose)
  implementation(libs.paging)
  implementation(libs.paging.compose)
}
