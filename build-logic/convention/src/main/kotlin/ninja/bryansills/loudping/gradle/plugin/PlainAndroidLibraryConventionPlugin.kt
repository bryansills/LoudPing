package ninja.bryansills.loudping.gradle.plugin

import com.android.build.gradle.LibraryExtension
import ninja.bryansills.configureAndroid
import ninja.bryansills.configureDependencyAnalysis
import ninja.bryansills.id
import ninja.bryansills.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class PlainAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins {
            id("com.android.library")
        }
        configureAndroid()
        configureDependencyAnalysis()
    }
}
