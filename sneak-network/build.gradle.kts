plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":sneak"))
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.sneak.network"
}
