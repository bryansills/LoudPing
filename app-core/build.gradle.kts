plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudchirp.app.core"
}

dependencies {
    implementation(project(":session"))
    implementation(project(":network-auth"))
    implementation(project(":time"))
    implementation(project(":android-app-res"))

    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.runtime.core)
    implementation(libs.navigation.compose.typed)
}
