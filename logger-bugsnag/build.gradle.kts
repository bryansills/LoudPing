plugins {
    alias(libs.plugins.loudping.android.library.kotlin.plain)
}

dependencies {
    implementation(projects.logger)
    implementation(libs.bugsnag)
}
