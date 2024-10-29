plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

android {
    namespace = "ninja.bryansills.loudping.ui.playedtracks"
}

dependencies {
    implementation(projects.database)
    implementation(projects.appTheme)
}
