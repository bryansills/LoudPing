plugins {
    alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
    implementation(projects.session)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.foreman)
    implementation(libs.androidx.compose.icons)
}
