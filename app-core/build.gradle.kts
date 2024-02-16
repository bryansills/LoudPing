plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudchirp.app.core"
}

dependencies {
    implementation(project(":session"))

    implementation(libs.androidx.compose.navigation)
}
