plugins {
    id("ninja.bryansills.android.library.kotlin")
}

android {
    namespace = "ninja.bryansills.loudping.auth.ui"
}

dependencies {
    implementation(libs.androidx.browser)
    implementation(libs.activity.ktx)
    implementation(libs.viewmodel)
}
