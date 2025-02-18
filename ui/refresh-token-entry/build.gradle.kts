plugins { id("ninja.bryansills.android.library.compose.screen") }

android { namespace = "ninja.bryansills.loudping.ui.refreshtokenentry" }

dependencies {
    implementation(projects.networkAuth)
    implementation(projects.appTheme)
    implementation(projects.androidAppRes)
    implementation(projects.di)
    implementation(projects.ui.destinations)
}
