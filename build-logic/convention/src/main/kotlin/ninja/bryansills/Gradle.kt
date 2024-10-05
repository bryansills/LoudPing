package ninja.bryansills

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun Project.plugins(action: PluginContainer.() -> Unit) = with(plugins) { action() }

internal fun Project.dependencies(action: DependencyHandler.() -> Unit) = with(dependencies) { action() }

internal fun PluginContainer.id(name: String) { apply(name) }

internal fun <T : ExternalDependency> DependencyHandlerScope.implementation(lib: Provider<T>) {
    "implementation"(lib)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.androidTestImplementation(lib: Provider<T>) {
    "androidTestImplementation"(lib)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.debugImplementation(lib: Provider<T>) {
    "debugImplementation"(lib)
}
