package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.loudping.gradle.configureAndroidLibrary
import ninja.bryansills.loudping.gradle.configureComposeAndroid
import ninja.bryansills.loudping.gradle.configureDagger
import ninja.bryansills.loudping.gradle.configureDependencyAnalysis
import ninja.bryansills.loudping.gradle.configureKotlinAndroid
import ninja.bryansills.loudping.gradle.configureNavigation
import ninja.bryansills.loudping.gradle.configureSpotless
import ninja.bryansills.loudping.gradle.util.id
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeAndroidScreenConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            id("com.android.library")
            id("org.jetbrains.kotlin.plugin.compose")
        }
        configureSpotless()
        configureKotlinAndroid()
        configureAndroidLibrary()
        configureDependencyAnalysis()
        configureComposeAndroid()
        configureDagger()
        configureNavigation()
    }
}
