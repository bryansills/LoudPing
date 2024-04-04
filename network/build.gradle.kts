plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
    kotlin("plugin.serialization")
}

android {
    namespace = "ninja.bryansills.louping.network"
}

dependencies {
    implementation(project(":network-auth"))
    implementation(project(":sneak-network"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.runtime)
    implementation(libs.okhttp)
}
