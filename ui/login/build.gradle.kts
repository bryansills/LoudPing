plugins {
  alias(libs.plugins.loudping.android.library.kotlin.dagger)
  alias(libs.plugins.parcelize)
}

android {
  androidResources.enable = true

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation(platform(libs.compose.bom))

  api(projects.networkAuth)
  api(projects.time)
  api(libs.appcompat)
  api(libs.androidx.activity)
  api(libs.androidx.viewmodel.savedstate)
  api(libs.dagger)
  api(libs.hilt.core)
  api(libs.javax.inject)
  api(libs.coroutines)

  implementation(projects.appTheme)
  implementation(libs.material)
  implementation(libs.androidx.browser)
  implementation(libs.androidx.annotation)
  implementation(libs.androidx.core)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtime)
  implementation(libs.parcelize.runtime)
}
