package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.alias
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Project

internal fun Project.configureDependencyAnalysis() {
    plugins {
        alias(libs.plugins.dependency.analysis)
    }
}
