plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

android {
    namespace = "ninja.bryansills.loudping.app.core"
}

dependencies {
    implementation(projects.session)
    implementation(projects.networkAuth)
    implementation(projects.time)
    implementation(projects.androidAppRes)
    implementation(projects.appTheme)
    implementation(projects.ui.home)

    implementation(libs.androidx.compose.activity)
    implementation(libs.activity.ktx)
    implementation(libs.coil)
    implementation(libs.coil.compose)
}
