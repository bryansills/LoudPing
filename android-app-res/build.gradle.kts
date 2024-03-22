plugins {
    id("ninja-bryansills-compose-android-lib")
}

android {
    namespace = "ninja.bryansills.loudping.res"

    buildFeatures {
        resValues = true
    }
}
