plugins {
    alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
    implementation(projects.networkAuth)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.foreman)
    implementation(libs.kotlinx.datetime)
    implementation(libs.workmanager)
}
