plugins {
    id("ninja.bryansills.android.library.kotlin.dagger")
    alias(libs.plugins.parcelize)
}

android {
    androidResources.enable = true

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.session)
    implementation(projects.networkAuth)
    implementation(projects.time)
    implementation(projects.appTheme)

    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.viewmodel)
    implementation(libs.androidx.browser)
}
