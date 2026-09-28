package ninja.bryansills.loudping.gradle.plugin

import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import ninja.bryansills.loudping.gradle.util.alias
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class PublishingConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) =
    with(target) {
      plugins {
        alias(libs.plugins.dependency.guard)
      }

      pluginManager.withPlugin("com.android.application") {
        configure<DependencyGuardPluginExtension> {
          configuration("releaseCompileClasspath")
          configuration("releaseRuntimeClasspath")
        }
        //        tasks
        //          .matching { it.name == "dependencyGuard" }
        //          .configureEach {
        //            onlyIf("") {
        //              // I wanna skip this task unless I am making a release APK or AAB
        //              false
        //            }
        //          }
      }

      pluginManager.withPlugin("com.android.library") {
        configure<DependencyGuardPluginExtension> { configuration("releaseRuntimeClasspath") }
      }

      pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
        configure<DependencyGuardPluginExtension> { configuration("androidRuntimeClasspath") }
      }

      pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        configure<DependencyGuardPluginExtension> { configuration("jvmRuntimeClasspath") }
      }

      pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        configure<DependencyGuardPluginExtension> { configuration("runtimeClasspath") }
      }
    }
}
