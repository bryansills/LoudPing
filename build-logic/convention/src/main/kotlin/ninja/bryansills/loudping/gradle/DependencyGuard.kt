package ninja.bryansills.loudping.gradle

import com.diffplug.gradle.spotless.SpotlessTask
import ninja.bryansills.loudping.gradle.util.dependencyGuard
import ninja.bryansills.loudping.gradle.util.id
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDependencyGuard() {
    plugins {
        id("com.dropbox.dependency-guard")
    }

    dependencyGuard {
        configuration("releaseCompileClasspath") { tree = true }
        configuration("releaseRuntimeClasspath") { tree = true }
    }

    tasks.dependencyGuard.configure {
        mustRunAfter(tasks.withType<SpotlessTask>())
    }
}
