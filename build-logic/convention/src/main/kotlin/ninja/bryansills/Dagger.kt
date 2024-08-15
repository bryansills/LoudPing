package ninja.bryansills

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureDagger() {
    plugins {
        apply("com.google.devtools.ksp")
        apply("com.google.dagger.hilt.android")
    }
    dependencies {
        "implementation"(libs.findLibrary("hilt-android").get())
        "ksp"(libs.findLibrary("hilt-compiler").get())
        "implementation"(libs.findLibrary("hilt-navigation-compose").get())
        "implementation"(libs.findLibrary("viewmodel").get())
    }
}
