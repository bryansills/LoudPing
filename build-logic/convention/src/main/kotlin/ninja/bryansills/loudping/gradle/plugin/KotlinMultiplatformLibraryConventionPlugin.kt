package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.loudping.gradle.configureDependencyAnalysis
import ninja.bryansills.loudping.gradle.configureKotlinMultiplatform
import ninja.bryansills.loudping.gradle.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        configureSpotless()
        configureKotlinMultiplatform()
        configureDependencyAnalysis()
    }
}
