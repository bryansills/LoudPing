plugins {
    id("ninja.bryansills.android.library.kotlin")
}

dependencies {
    implementation(projects.logger)
    implementation(libs.bugsnag)
}
