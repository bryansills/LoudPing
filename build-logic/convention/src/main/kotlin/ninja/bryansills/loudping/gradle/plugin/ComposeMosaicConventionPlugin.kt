package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.loudping.gradle.configureComposeCompiler
import ninja.bryansills.loudping.gradle.configureDependencyAnalysis
import ninja.bryansills.loudping.gradle.configureKotlinJvmOnly
import ninja.bryansills.loudping.gradle.configureSpotless
import ninja.bryansills.loudping.gradle.util.id
import ninja.bryansills.loudping.gradle.util.implementation
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposeMosaicConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            id("org.jetbrains.kotlin.jvm")
            id("org.jetbrains.kotlin.plugin.compose")
            id("org.gradle.application")
        }
        configureSpotless()
        configureKotlinJvmOnly()
        configureDependencyAnalysis()
        configureComposeCompiler()
        dependencies {
            implementation(libs.mosaic)
        }
    }
}
