plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.datastore)
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.storage"
}
