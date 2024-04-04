plugins {
    id("ninja-bryansills-kmp")
}

kotlin {
    jvm()
}

android {
    namespace = "ninja.bryansills.loudping.sneak.network"
}

dependencies {
    implementation(project(":sneak"))
}
