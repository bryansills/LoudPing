plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
    kotlin("plugin.serialization")
}

android {
    namespace = "ninja.bryansills.loudping.network.auth"
}

dependencies {
    implementation(project(":storage"))
    implementation(project(":time"))
    implementation(project(":app-sneak"))
    implementation(project(":session"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.runtime)
    implementation(libs.okhttp)
}
