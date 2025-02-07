package ninja.bryansills.loudping.gradle.plugin

import gradle.kotlin.dsl.accessors._4e139ca99259fc34798667ee207c554e.implementation
import ninja.bryansills.configureComposeCompiler
import ninja.bryansills.configureDependencyAnalysis
import ninja.bryansills.configureKotlin
import ninja.bryansills.configureSpotless
import ninja.bryansills.dependencies
import ninja.bryansills.get
import ninja.bryansills.id
import ninja.bryansills.libs
import ninja.bryansills.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeMosaicConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            id("org.jetbrains.kotlin.jvm")
            id("org.jetbrains.kotlin.plugin.compose")
            id("org.gradle.application")
        }
        configureSpotless()
        configureKotlin()
        configureDependencyAnalysis()
        configureComposeCompiler()
        dependencies {
            implementation(libs["mosaic"])
        }
    }
}
