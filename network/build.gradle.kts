plugins {
    id("ninja-bryansills-kmp")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":network-auth"))
            implementation(project(":sneak-network"))

            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization)
            implementation(libs.kotlinx.serialization.runtime)
            implementation(libs.okhttp)
            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "ninja.bryansills.louping.network"
}
