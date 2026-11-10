package ninja.bryansills.loudping.gradle.plugin

import androidx.build.configureLint
import ninja.bryansills.loudping.gradle.configureAndroidLibrary
import ninja.bryansills.loudping.gradle.configureComposeAndroid
import ninja.bryansills.loudping.gradle.configureKotlinAndroid
import ninja.bryansills.loudping.gradle.configureMetro
import ninja.bryansills.loudping.gradle.configureNavigation
import ninja.bryansills.loudping.gradle.util.alias
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeScreenConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) =
    with(target) {
      plugins {
        alias(libs.plugins.android.library)
        alias(libs.plugins.compose.compiler)
      }
      configureMetro()
      configureKotlinAndroid()
      configureAndroidLibrary()
      configureLint(isCompose = true)
      configureComposeAndroid()
      configureNavigation()
    }
}
