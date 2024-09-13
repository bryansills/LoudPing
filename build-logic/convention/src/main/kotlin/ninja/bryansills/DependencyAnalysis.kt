package ninja.bryansills

import org.gradle.api.Project

internal fun Project.configureDependencyAnalysis() {
    plugins {
        id("com.autonomousapps.dependency-analysis")
    }
}
