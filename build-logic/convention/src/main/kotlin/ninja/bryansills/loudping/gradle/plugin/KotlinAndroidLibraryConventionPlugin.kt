package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.configureAndroid
import ninja.bryansills.configureDependencyAnalysis
import ninja.bryansills.configureKotlin
import ninja.bryansills.configureSpotless
import ninja.bryansills.id
import ninja.bryansills.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            id("com.android.library")
            id("org.jetbrains.kotlin.android")
        }
        configureSpotless()
        configureKotlin()
        configureAndroid()
        configureDependencyAnalysis()
    }
}
