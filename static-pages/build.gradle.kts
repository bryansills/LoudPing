plugins {
    kotlin("jvm")
    application
}

application {
    mainClass = "ninja.bryansills.loudping.staticpages.MainKt"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.10.1")
    implementation("com.squareup.okio:okio:3.7.0")
}