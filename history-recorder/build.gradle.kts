plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(project(":database"))
            implementation(project(":network"))
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.history.recorder"
}
