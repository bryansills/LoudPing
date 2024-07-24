plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudping.app.core"
}

dependencies {
    implementation(projects.session)
    implementation(projects.networkAuth)
    implementation(projects.time)
    implementation(projects.androidAppRes)

    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.runtime.core)
    implementation(libs.navigation.compose.typed)
    implementation(libs.coil)
    implementation(libs.coil.compose)
}
