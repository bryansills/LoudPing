plugins {
    alias(libs.plugins.loudping.jvm)
}

dependencies {
    api(libs.datastore)
    implementation(libs.coroutines)
}
