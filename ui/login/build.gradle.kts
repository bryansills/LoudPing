plugins {
    id("ninja.bryansills.android.library.kotlin.dagger")
}

android {
    namespace = "ninja.bryansills.loudping.ui.login"

    buildFeatures {
        androidResources = true
        viewBinding = true
    }
}

dependencies {
    implementation(projects.session)
    implementation(projects.networkAuth)
    implementation(projects.time)
    implementation(projects.appTheme)

    implementation(libs.activity.ktx)
    implementation(libs.viewmodel)
    implementation(libs.androidx.browser)
}
