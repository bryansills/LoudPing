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
            implementation(projects.time)

            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization)
            implementation(libs.kotlinx.serialization.runtime)
            implementation(libs.okhttp)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.network"
}
