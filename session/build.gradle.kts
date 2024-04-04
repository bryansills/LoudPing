plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":storage"))
        }
    }
}

android {
    namespace = "ninja.bryansills.loudping.session"
}
