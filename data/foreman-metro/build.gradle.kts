plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
  alias(libs.plugins.metro)
}

dependencies {
  api(libs.metro.runtime)
  api(libs.workmanager)
}
