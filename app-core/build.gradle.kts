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
    implementation(projects.ui.destinations)
    implementation(projects.ui.login)
    implementation(projects.ui.settings)
    implementation(projects.ui.playedTracks)
    implementation(projects.ui.refreshTokenEntry)

    implementation(libs.androidx.compose.activity)
    implementation(libs.activity.ktx)
    implementation(libs.coil)
    implementation(libs.coil.compose)
}
