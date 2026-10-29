plugins {
  alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
  api(projects.core.model)
  api(projects.database.core)
  api(libs.compose.foundation)
  api(libs.compose.runtime)
  api(libs.compose.ui)
  api(libs.androidx.viewmodel.core)
  api(libs.dagger)
  api(libs.javax.inject)
  api(libs.coroutines)

  implementation(projects.appTheme)
  implementation(libs.paging)
  implementation(libs.paging.compose)
  implementation(libs.compose.foundation.layout)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.text)
  implementation(libs.compose.ui.unit)
  implementation(libs.hilt.viewmodel.compose)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.hilt.core)
}
