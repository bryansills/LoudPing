plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.storage)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.session"
}
