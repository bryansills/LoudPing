plugins {
    alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
    implementation(projects.networkAuth)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.di)
    implementation(projects.ui.destinations)
}
