plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudchirp.app.core"
}

dependencies {
    implementation(project(":session"))

    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.runtime.core)
    implementation(libs.navigation.compose.typed)
}
