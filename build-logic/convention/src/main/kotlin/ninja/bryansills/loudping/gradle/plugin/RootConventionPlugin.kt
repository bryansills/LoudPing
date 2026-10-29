package ninja.bryansills.loudping.gradle.plugin

import com.autonomousapps.DependencyAnalysisExtension
import com.google.samples.apps.nowinandroid.configureGraphTasks
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.configuration.BuildFeatures
import org.gradle.kotlin.dsl.configure

abstract class RootConventionPlugin : Plugin<Project> {

  @get:Inject abstract val buildFeatures: BuildFeatures

  override fun apply(target: Project) =
    with(target) {
      require(target.path == ":")

      configureDagp()

      if (!buildFeatures.isIsolatedProjectsEnabled()) {
        target.subprojects { configureGraphTasks() }
      }
    }
}

private fun Project.configureDagp() {
  dependencyAnalysis {
    useTypesafeProjectAccessors(true)
    issues {
      all { onAny { severity("fail") } }

      // KMP and CMP do some stuff that doesn't jive well with DAGP. Ignoring the issues for now.
      project(":app-theme") {
        onUnusedDependencies { severity("ignore") }
        onUsedTransitiveDependencies { severity("ignore") }
      }
      project(":database:core") {
        onUsedTransitiveDependencies { severity("ignore") }
      }
    }
  }
}

private fun Project.dependencyAnalysis(block: DependencyAnalysisExtension.() -> Unit) {
  this.configure<DependencyAnalysisExtension>(block)
}

private fun BuildFeatures.isIsolatedProjectsEnabled(): Boolean {
  return isolatedProjects.active.getOrElse(false)
}
