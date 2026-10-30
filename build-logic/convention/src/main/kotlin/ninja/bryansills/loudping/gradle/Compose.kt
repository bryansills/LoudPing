package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.androidTestImplementation
import ninja.bryansills.loudping.gradle.util.debugImplementation
import ninja.bryansills.loudping.gradle.util.implementation
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.lintChecks
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid() {
  dependencies {
    val composeBom = libs.compose.bom
    implementation(platform(composeBom))
    androidTestImplementation(platform(composeBom))
    debugImplementation(libs.compose.ui.tooling)
    lintChecks(libs.compose.lint)
  }
}
