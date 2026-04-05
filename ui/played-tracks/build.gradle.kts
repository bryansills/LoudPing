plugins {
    alias(libs.plugins.loudping.android.library.compose)
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.database.core)
    implementation(projects.appTheme)
    implementation(libs.kotlinx.datetime)
    implementation(libs.paging)
    implementation(libs.paging.compose)
}
