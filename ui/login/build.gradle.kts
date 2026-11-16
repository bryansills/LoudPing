plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
  alias(libs.plugins.metro)
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

  api(projects.core.di)
  api(projects.networkAuth)
  api(projects.time)
  api(libs.appcompat)
  api(libs.androidx.activity)
  api(libs.androidx.viewmodel.savedstate)
  api(libs.coroutines)
  api(libs.metro.android)
  api(libs.metro.runtime)
  api(libs.metro.viewmodel)

  implementation(projects.ui.theme)
  implementation(libs.androidx.annotation)
  implementation(libs.androidx.browser)
  implementation(libs.androidx.core)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtime)
  implementation(libs.material)
  implementation(libs.parcelize.runtime)
}
