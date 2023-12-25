import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val LibrariesForLibs.javaVersion
    get(): JavaVersion = JavaVersion.toVersion(versions.java.version.get())

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = libs.javaVersion
    targetCompatibility = libs.javaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = libs.javaVersion.toString()
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}