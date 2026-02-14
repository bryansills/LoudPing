plugins {
    alias(libs.plugins.loudping.jvm)
    application
    alias(libs.plugins.kotlinx.serialization)
}

application {
    mainClass = "ninja.bryansills.loudping.readability.MainKt"
}

dependencies {
    implementation(projects.coroutinesExt)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.jaxb3)
    implementation(libs.jaxb3Api)
    implementation(libs.jaxb3Impl)
    implementation(libs.htmlunit)
    implementation(libs.kotlinx.serialization.runtime.json)
}
