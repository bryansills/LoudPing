plugins {
    id("ninja-bryansills-kmp")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.networkAuth)
            implementation(projects.sneakNetwork)

            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization)
            implementation(libs.kotlinx.serialization.runtime)
            implementation(libs.okhttp)
            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.network"
}
