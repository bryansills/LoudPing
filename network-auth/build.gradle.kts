plugins {
    id("ninja-bryansills-kmp")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.storage)
            implementation(projects.time)
            implementation(projects.sneakNetwork)
            implementation(projects.session)

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
