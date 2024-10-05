package ninja.bryansills

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
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

operator fun VersionCatalog.get(stringLibName: String): Provider<MinimalExternalModuleDependency> {
    return this.findLibrary(stringLibName).get()
}
