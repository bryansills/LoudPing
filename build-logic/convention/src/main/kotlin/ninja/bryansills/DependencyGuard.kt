package ninja.bryansills

import com.diffplug.gradle.spotless.SpotlessTask
import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDependencyGuard() {
    plugins {
        apply("com.dropbox.dependency-guard")
    }

    dependencyGuard {
        configuration("releaseCompileClasspath") { tree = true }
        configuration("releaseRuntimeClasspath") { tree = true }
    }

    tasks.dependencyGuard.configure {
        mustRunAfter(tasks.withType<SpotlessTask>())
    }
}


private fun Project.dependencyGuard(configure: Action<DependencyGuardPluginExtension>): Unit =
    (this as ExtensionAware).extensions.configure("dependencyGuard", configure)

private val TaskContainer.dependencyGuard: TaskProvider<DefaultTask>
    get() = named<DefaultTask>("dependencyGuard")
