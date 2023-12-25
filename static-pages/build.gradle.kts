plugins {
    kotlin("jvm")
    application
}

application {
    mainClass = "ninja.bryansills.loudping.staticpages.MainKt"
}

dependencies {
    implementation(libs.kotlinx.html)
    implementation(libs.okio)
}