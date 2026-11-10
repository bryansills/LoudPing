plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(libs.androidx.viewmodel.core)
  api(libs.coroutines)
  api(libs.metro.runtime)
  api(libs.metro.viewmodel)
}
