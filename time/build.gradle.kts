plugins {
    id("ninja-bryansills-compose-dagger-android-lib")
}

android {
    namespace = "ninja.bryansills.loudping.time"
}

dependencies {
    api(libs.kotlinx.datetime)
}
