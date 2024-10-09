plugins {
    id("ninja.bryansills.android.library.kotlin")
}

android {
    namespace = "ninja.bryansills.loudping.ui.login"

    buildFeatures {
        androidResources = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.viewmodel)
}
