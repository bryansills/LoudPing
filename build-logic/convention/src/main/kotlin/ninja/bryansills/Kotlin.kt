package ninja.bryansills

import org.gradle.api.Project

internal fun Project.configureKotlin() {
    // Configure Java to use our chosen language level. Kotlin will automatically pick this up
    configureJava()
}
