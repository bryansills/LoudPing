plugins {
  alias(libs.plugins.loudping.android.library.kotlin.plain)
}

dependencies {
  api(projects.logger)
  implementation(libs.bugsnag.core)
  runtimeOnly(libs.bugsnag.anr)
  runtimeOnly(libs.bugsnag.ndk)
}
