plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudchirp.app.core"
}

dependencies {
    implementation(libs.androidx.compose.navigation)
}
