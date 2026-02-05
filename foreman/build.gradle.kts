plugins {
    id("ninja.bryansills.android.library.kotlin.dagger")
}

dependencies {
    implementation(projects.historyRecorder)
    implementation(projects.storage)
    implementation(projects.time)
    implementation(projects.logger)
    api(libs.workmanager)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.workmanager.hilt)
    implementation(libs.kotlinx.datetime)
}
