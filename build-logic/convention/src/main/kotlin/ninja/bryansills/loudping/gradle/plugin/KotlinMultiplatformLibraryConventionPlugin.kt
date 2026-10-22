package ninja.bryansills.loudping.gradle.plugin

import androidx.build.configureLint
import ninja.bryansills.loudping.gradle.configureDependencyAnalysis
import ninja.bryansills.loudping.gradle.configureKotlinMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) =
    with(target) {
      configureKotlinMultiplatform()
      configureDependencyAnalysis()
      configureLint()
    }
}
