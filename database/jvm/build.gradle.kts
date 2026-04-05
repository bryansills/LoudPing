plugins {
    alias(libs.plugins.loudping.jvm)
}

dependencies {
    implementation(projects.database.core)
    implementation(libs.sqldelight.jvm.driver)
}
