plugins {
  alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
  api(projects.session)
  api(projects.foreman)
  api(libs.compose.foundation.layout)
  api(libs.compose.runtime)
  api(libs.compose.ui)
  api(libs.javax.inject)
  api(libs.coroutines)
  api(libs.dagger)
  api(libs.androidx.viewmodel.core)

  implementation(projects.appTheme)
  implementation(projects.androidAppRes)
  implementation(libs.compose.icons)
  implementation(libs.compose.foundation)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.text)
  implementation(libs.compose.ui.unit)
  implementation(libs.hilt.viewmodel.compose)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.hilt.core)
}
