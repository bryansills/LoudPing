package ninja.bryansills

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless() {
    plugins {
        apply("com.diffplug.spotless")
    }

    spotless {
        format("misc") {
            target("*.md", ".gitignore")
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlin {
            ktlint(libs.findVersion("ktlint").get().requiredVersion)
            target("**/*.kt")
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlinGradle {
            ktlint(libs.findVersion("ktlint").get().requiredVersion)
            target("**/*.gradle.kts")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

private fun Project.spotless(action: SpotlessExtension.() -> Unit) = extensions.configure<SpotlessExtension>(action)
