package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.loudping.gradle.configureAndroidLibrary
import ninja.bryansills.loudping.gradle.configureDagger
import ninja.bryansills.loudping.gradle.configureDependencyAnalysis
import ninja.bryansills.loudping.gradle.configureKotlinAndroid
import ninja.bryansills.loudping.gradle.configureSpotless
import ninja.bryansills.loudping.gradle.util.alias
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinAndroidDaggerLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            alias(libs.plugins.android.library)
        }
        configureSpotless()
        configureKotlinAndroid()
        configureAndroidLibrary()
        configureDependencyAnalysis()
        configureDagger()
    }
}
