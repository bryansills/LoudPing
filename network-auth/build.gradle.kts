plugins {
    id("ninja-bryansills-kmp")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":storage"))
            implementation(project(":time"))
            implementation(project(":sneak-network"))
            implementation(project(":session"))

            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization)
            implementation(libs.kotlinx.serialization.runtime)
            implementation(libs.okhttp)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.network.auth"
}
