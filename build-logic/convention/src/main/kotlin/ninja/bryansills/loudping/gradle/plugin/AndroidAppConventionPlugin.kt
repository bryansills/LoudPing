package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.configureAndroid
import ninja.bryansills.configureComposeAndroid
import ninja.bryansills.configureDagger
import ninja.bryansills.configureDependencyAnalysis
import ninja.bryansills.configureDependencyGuard
import ninja.bryansills.configureKotlin
import ninja.bryansills.configureSpotless
import ninja.bryansills.id
import ninja.bryansills.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            id("com.android.application")
            id("org.jetbrains.kotlin.android")
            id("org.jetbrains.kotlin.plugin.compose")
        }
        configureSpotless()
        configureKotlin()
        configureAndroid()
        configureDependencyGuard()
        configureDependencyAnalysis()
        configureComposeAndroid()
        configureDagger()
    }
}
