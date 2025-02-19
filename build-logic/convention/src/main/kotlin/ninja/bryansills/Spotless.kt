package ninja.bryansills

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless() {
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
            if (this@configureSpotless.path == ":") {
                // at the root, target the build-logic code
                target("build-logic/convention/src/**/*.kt")
            } else {
                target("src/**/*.kt")
            }
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlinGradle {
            ktlint(libs.versions.ktlint.get())
            if (this@configureSpotless.path == ":") {
                // additionally at the root, target the build-logic code
                target("*.gradle.kts", "build-logic/**/*.gradle.kts")
            } else {
                target("**/*.gradle.kts")
            }
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

private fun Project.spotless(action: SpotlessExtension.() -> Unit) = extensions.configure<SpotlessExtension>(action)
