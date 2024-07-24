plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(projects.database)
            implementation(projects.network)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.history.recorder"
}
