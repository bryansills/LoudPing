plugins {
    id("ninja-bryansills-compose-android-app")
}

android {
    namespace = "ninja.bryansills.loudping"

    defaultConfig {
        applicationId = "ninja.bryansills.loudping"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
//    implementation(project(":app-core"))
    implementation(libs.core.ktx)
    implementation(libs.androidx.compose.activity)
}
