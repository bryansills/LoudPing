package ninja.bryansills

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureDagger() {
    plugins {
        id("com.google.devtools.ksp")
        id("com.google.dagger.hilt.android")
    }
    dependencies {
        "implementation"(libs.findLibrary("hilt-android").get())
        "ksp"(libs.findLibrary("hilt-compiler").get())
    }
}
