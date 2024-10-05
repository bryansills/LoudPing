package ninja.bryansills

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureNavigation() {
    plugins {
        id("org.jetbrains.kotlin.plugin.serialization")
    }
    dependencies {
        implementation(libs["androidx-compose-navigation"])
        implementation(libs["hilt-navigation-compose"])
        implementation(libs["viewmodel"])
        implementation(libs["kotlinx-serialization-runtime"])
    }
}
