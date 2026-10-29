package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.alias
import ninja.bryansills.loudping.gradle.util.implementation
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureNavigation() {
  plugins { alias(libs.plugins.kotlinx.serialization) }
  dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.viewmodel.core)
    implementation(libs.kotlinx.serialization.runtime.json)
  }
}
