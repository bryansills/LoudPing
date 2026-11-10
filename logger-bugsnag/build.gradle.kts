plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.logger)
  api(libs.metro.runtime)
  implementation(libs.bugsnag.core)
  runtimeOnly(libs.bugsnag.anr)
  runtimeOnly(libs.bugsnag.ndk)
}
