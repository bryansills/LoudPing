package ninja.bryansills.loudping.gradle.util

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.plugin.use.PluginDependency

internal fun Project.plugins(action: PluginContainer.() -> Unit) = with(plugins) { action() }

internal fun PluginContainer.alias(provider: Provider<PluginDependency>) {
    apply(provider.get().pluginId)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.implementation(lib: Provider<T>) {
    "implementation"(lib)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.androidTestImplementation(lib: Provider<T>) {
    "androidTestImplementation"(lib)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.debugImplementation(lib: Provider<T>) {
    "debugImplementation"(lib)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.coreLibraryDesugaring(lib: Provider<T>) {
    "coreLibraryDesugaring"(lib)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.androidTestRuntimeOnly(lib: Provider<T>) {
    "androidTestRuntimeOnly"(lib)
}

internal fun <T : ExternalDependency> DependencyHandlerScope.ksp(lib: Provider<T>) {
    "ksp"(lib)
}
