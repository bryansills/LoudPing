plugins {
    alias(libs.plugins.loudping.jvm)
    application
    alias(libs.plugins.kotlinx.serialization)
}

application {
    mainClass = "ninja.bryansills.loudping.readability.MainKt"
}

dependencies {
    implementation(libs.htmlunit)
    implementation(libs.kotlinx.serialization.runtime.json)
}
