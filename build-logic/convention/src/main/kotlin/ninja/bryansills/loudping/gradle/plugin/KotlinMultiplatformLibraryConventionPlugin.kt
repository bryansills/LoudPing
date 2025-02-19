package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.configureDependencyAnalysis
import ninja.bryansills.configureKotlin
import ninja.bryansills.configureKotlinMultiplatform
import ninja.bryansills.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        configureSpotless()
        configureKotlinMultiplatform()
        configureKotlin()
        configureDependencyAnalysis()
    }
}
