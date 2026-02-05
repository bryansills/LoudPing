plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

dependencies {
    implementation(projects.networkAuth)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.foreman)
    implementation(libs.kotlinx.datetime)
    implementation(libs.workmanager)
}
