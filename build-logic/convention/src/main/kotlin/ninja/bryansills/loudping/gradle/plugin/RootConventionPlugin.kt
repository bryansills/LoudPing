package ninja.bryansills.loudping.gradle.plugin

import ninja.bryansills.loudping.gradle.ModulePaths
import ninja.bryansills.loudping.gradle.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project

class RootConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        configureSpotless(paths = ModulePaths.RootWithBuildLogic)
    }
}
