plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudping.app.sneak"
}

dependencies {
    implementation(project(":sneak"))
}
