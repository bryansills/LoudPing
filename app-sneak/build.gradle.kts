plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudchirp.app.sneak"
}

dependencies {
    implementation(project(":sneak"))
}
