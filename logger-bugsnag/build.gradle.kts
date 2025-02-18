plugins { id("ninja.bryansills.android.library.kotlin") }

android { namespace = "ninja.bryansills.loudping.logger.bugsnag" }

dependencies {
  implementation(projects.logger)
  implementation(libs.bugsnag)
}
