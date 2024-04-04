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
    implementation(project(":sneak-network"))
    implementation(project(":session"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.runtime)
    implementation(libs.okhttp)
}
