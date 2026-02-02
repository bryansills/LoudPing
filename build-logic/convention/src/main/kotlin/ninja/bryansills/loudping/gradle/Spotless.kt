package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.id
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import ninja.bryansills.loudping.gradle.util.spotless
import org.gradle.api.Project

internal fun Project.configureSpotless(paths: ModulePaths = ModulePaths.Standard) {
    plugins {
        id("com.diffplug.spotless")
    }

    spotless {
        format("misc") {
            target("*.md", ".gitignore")
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlin {
            ktlint(libs.versions.ktlint.get())
            target(paths.kotlin)
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlinGradle {
            ktlint(libs.versions.ktlint.get())
            target(paths.kotlinGradle)
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

/**
 * This is me being a bit clever. Generally, Spotless is configured on a per-module basis, but when configuring Spotless
 * for the root project, we toss in the paths for all the convention plugin stuff.
 */
internal enum class ModulePaths(
    val kotlin: List<String>,
    val kotlinGradle: List<String>
) {
    Standard(
        kotlin = listOf("src/**/*.kt"),
        kotlinGradle = listOf("./**/*.gradle.kts")
    ),
    RootWithBuildLogic(
        kotlin = listOf("build-logic/convention/src/**/*.kt"),
        kotlinGradle = listOf("*.gradle.kts", "build-logic/**/*.gradle.kts")
    )
}
