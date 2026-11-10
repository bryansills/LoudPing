plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.logger)
  implementation(libs.bugsnag.core)
  implementation(libs.metro.runtime)
  runtimeOnly(libs.bugsnag.anr)
  runtimeOnly(libs.bugsnag.ndk)
}
