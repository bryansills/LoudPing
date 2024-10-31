plugins {
    id("ninja.bryansills.android.library.kotlin.dagger")
}

android {
    namespace = "ninja.bryansills.loudping.foreman"
}

dependencies {
    implementation(projects.historyRecorder)
    implementation(projects.storage)
    implementation(projects.time)
    implementation(libs.workmanager)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.workmanager.hilt)
    implementation(libs.kotlinx.datetime)
}
