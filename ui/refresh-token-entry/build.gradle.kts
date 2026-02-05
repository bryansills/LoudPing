plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

dependencies {
    implementation(projects.networkAuth)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.di)
    implementation(projects.ui.destinations)
}
