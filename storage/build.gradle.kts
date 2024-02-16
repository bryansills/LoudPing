plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudchirp.storage"
}

dependencies {
    api(libs.datastore)
}
