package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.configureAndroid
import ninja.bryansills.configureDependencyAnalysis
import ninja.bryansills.configureKotlin
import ninja.bryansills.configureKotlinMultiplatform
import ninja.bryansills.configureSpotless
import ninja.bryansills.id
import ninja.bryansills.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class SqldelightConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            id("com.android.library")
            id("app.cash.sqldelight")
        }
        configureSpotless()
        configureAndroid()
        configureKotlinMultiplatform()
        configureKotlin()
        configureDependencyAnalysis()
    }
}
