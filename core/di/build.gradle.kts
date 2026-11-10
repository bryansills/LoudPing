plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
}

dependencies {
  implementation(libs.androidx.viewmodel.core)
  implementation(libs.metro.runtime)
  implementation(libs.metro.viewmodel)
}
