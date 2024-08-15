package ninja.bryansills

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer

internal fun Project.plugins(action: PluginContainer.() -> Unit) = with(plugins) { action() }

internal fun Project.dependencies(action: DependencyHandler.() -> Unit) = with(dependencies) { action() }
