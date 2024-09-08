package ninja.bryansills

import org.gradle.api.Project

fun Project.configureDependencyAnalysis() {
    plugins {
        id("com.autonomousapps.dependency-analysis")
    }
}
