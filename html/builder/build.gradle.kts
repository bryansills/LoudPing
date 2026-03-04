plugins {
    alias(libs.plugins.loudping.jvm)
    application
}

application {
    mainClass = "ninja.bryansills.loudping.html.builder.MainKt"
}

dependencies {
    implementation(projects.coroutinesExt)
    implementation(projects.html.callback)
    implementation(projects.html.core)
    implementation(projects.html.digest)
    implementation(projects.time)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.jaxb3)
    implementation(libs.jaxb3Api)
    implementation(libs.jaxb3Impl)
    implementation(libs.htmlunit)
    implementation(libs.kotlinx.serialization.runtime.json)
    implementation(libs.kotlinx.html)
    implementation(libs.okio)
}
