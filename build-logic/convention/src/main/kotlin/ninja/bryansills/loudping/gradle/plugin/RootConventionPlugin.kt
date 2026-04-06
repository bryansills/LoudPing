package ninja.bryansills.loudping.gradle.plugin

import com.google.samples.apps.nowinandroid.configureGraphTasks
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.configuration.BuildFeatures

abstract class RootConventionPlugin : Plugin<Project> {

  @get:Inject abstract val buildFeatures: BuildFeatures

  override fun apply(target: Project) =
    with(target) {
      require(target.path == ":")
      if (!buildFeatures.isIsolatedProjectsEnabled()) {
        target.subprojects { configureGraphTasks() }
      }
    }
}

private fun BuildFeatures.isIsolatedProjectsEnabled(): Boolean {
  return isolatedProjects.active.getOrElse(false)
}
