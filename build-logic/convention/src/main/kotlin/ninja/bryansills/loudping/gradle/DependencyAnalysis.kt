package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.id
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Project

internal fun Project.configureDependencyAnalysis() {
    plugins {
        id("com.autonomousapps.dependency-analysis")
    }
}
