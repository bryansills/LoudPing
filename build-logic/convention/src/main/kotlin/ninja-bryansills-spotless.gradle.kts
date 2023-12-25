import ninja.bryansills.libs

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