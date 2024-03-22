plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudping.storage"
}

dependencies {
    api(libs.datastore)
}
