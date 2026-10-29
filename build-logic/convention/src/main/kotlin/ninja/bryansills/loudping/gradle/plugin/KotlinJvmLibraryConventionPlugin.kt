package ninja.bryansills.loudping.gradle.plugin

import androidx.build.configureLint
import ninja.bryansills.loudping.gradle.configureKotlinJvmOnly
import ninja.bryansills.loudping.gradle.util.alias
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinJvmLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) =
    with(target) {
      plugins { alias(libs.plugins.kotlin.jvm) }
      configureKotlinJvmOnly()
      configureLint()
    }
}
