plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.sneak)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.sneak.network"
}
