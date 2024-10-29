plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

android {
    namespace = "ninja.bryansills.loudping.ui.home"
}

dependencies {
    implementation(projects.session)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.foreman)
}
