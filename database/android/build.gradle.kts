plugins {
    alias(libs.plugins.loudping.android.library.kotlin.plain)
}

dependencies {
    implementation(projects.database.core)
    implementation(libs.sqldelight.android.driver)
}
