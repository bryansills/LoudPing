plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
  alias(libs.plugins.metro)
}

dependencies {
  implementation(libs.metro.runtime)
  implementation(libs.workmanager)
}
