plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

dependencies {
    implementation(projects.session)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.foreman)
    implementation(libs.androidx.compose.icons)
}
