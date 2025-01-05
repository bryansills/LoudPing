plugins {
    id("ninja.bryansills.root")
    alias(libs.plugins.kotlin.jvm)
    application
}

application {
    mainClass = "ninja.bryansills.loudping.deephistory.runner.MainKt"
}

dependencies {
    implementation(projects.deepHistoryModel)
    implementation(libs.okio)
    implementation(libs.kotlinx.serialization.runtime)
}
