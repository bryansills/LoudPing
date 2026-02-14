package ninja.bryansills.loudping.gradle.util

import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named

internal fun Project.dependencyGuard(configure: Action<DependencyGuardPluginExtension>): Unit = (this as ExtensionAware).extensions.configure("dependencyGuard", configure)

internal val TaskContainer.dependencyGuard: TaskProvider<DefaultTask>
    get() = named<DefaultTask>("dependencyGuard")
