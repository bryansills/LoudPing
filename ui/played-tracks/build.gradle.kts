plugins {
    id("ninja.bryansills.android.library.compose.screen")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.database)
    implementation(projects.appTheme)
    implementation(libs.kotlinx.datetime)
    implementation(libs.paging)
    implementation(libs.paging.compose)
}
