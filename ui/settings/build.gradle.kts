plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

android {
    namespace = "ninja.bryansills.loudping.ui.settings"
}

dependencies {
    implementation(projects.networkAuth)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(libs.kotlinx.datetime)
}
