plugins {
  alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
  api(projects.networkAuth)
  api(projects.di)
  api(libs.compose.foundation.layout)
  api(libs.compose.runtime)
  api(libs.androidx.viewmodel.core)
  api(libs.androidx.navigation.common)
  api(libs.dagger)
  implementation(libs.hilt.android)
  api(libs.javax.inject)
  api(libs.coroutines)
  api(libs.compose.ui.text)

  implementation(projects.ui.destinations)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.unit)
  implementation(libs.hilt.viewmodel.compose)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.viewmodel.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.hilt.navigation.compose)
  implementation(libs.hilt.core)
}
