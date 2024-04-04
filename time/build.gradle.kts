plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.time"
}
