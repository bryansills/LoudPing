/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.build

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.android.build.api.dsl.Lint
import com.android.build.api.variant.KotlinMultiplatformAndroidComponentsExtension
import com.android.build.api.variant.LintLifecycleExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.KotlinMultiplatformAndroidPlugin
import java.io.File
import ninja.bryansills.loudping.gradle.util.agpKmpExtension
import ninja.bryansills.loudping.gradle.util.lintBaseline
import ninja.bryansills.loudping.gradle.util.multiplatformExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin

/**
 * Single entry point to Android Lint configuration.
 *
 * This whole file is mostly taken from the AndroidX repo. Another solid (and probably easier to
 * comprehend) example can be found here:
 * https://github.com/niyajali/hedvig-android/blob/develop/build-logic/convention/src/main/kotlin/HedvigLintConventionPlugin.kt
 */
fun Project.configureLint() {
  project.plugins.configureEach {
    when (this) {
      is AppPlugin -> configureAndroidProjectForLint(isApp = true)
      is LibraryPlugin -> configureAndroidProjectForLint(isApp = false)
      is KotlinMultiplatformAndroidPlugin ->
        configureAndroidMultiplatformProjectForLint(
          agpKmpExtension,
          extensions.getByType<KotlinMultiplatformAndroidComponentsExtension>(),
        )
      // Only configure non-multiplatform Java projects via JavaPlugin. Multiplatform
      // projects targeting Java (e.g. `jvm { withJava() }`) are configured via
      // KotlinBasePlugin.
      is JavaPlugin ->
        if (project.multiplatformExtension == null) {
          configureNonAndroidProjectForLint()
        }
      // Only configure non-Android multiplatform projects via KotlinBasePlugin.
      // Multiplatform projects targeting Android (e.g. `id("com.android.library")`) are
      // configured via AppPlugin or LibraryPlugin.
      is KotlinBasePlugin ->
        if (
          project.multiplatformExtension != null &&
            !project.plugins.hasPlugin(AppPlugin::class.java) &&
            !project.plugins.hasPlugin(LibraryPlugin::class.java) &&
            !project.plugins.hasPlugin(KotlinMultiplatformAndroidPlugin::class.java)
        ) {
          configureNonAndroidProjectForLint()
        }
    }
  }
}

/** Android Lint configuration entry point for Android projects. */
private fun Project.configureAndroidProjectForLint(isApp: Boolean) =
  extensions.findByType(LintLifecycleExtension::class.java)!!.finalizeDsl { lint ->
    configureLint(lint, isApp = isApp)
  }

private fun Project.configureAndroidMultiplatformProjectForLint(
  extension: KotlinMultiplatformAndroidLibraryTarget,
  componentsExtension: KotlinMultiplatformAndroidComponentsExtension,
) {
  componentsExtension.finalizeDsl {
    apply(mapOf("plugin" to "com.android.lint"))

    configureLint(extension.lint, isApp = false)
  }
  // TODO (b/442881540): remove this workaround
  mergeMultiplatformBaselines()
}

/**
 * For KMP projects with multiple lint tasks, the baseline files from each task overwrite each
 * other, so the last one to run will be the final baseline, meaning issues from the other tasks
 * won't end up in the final baseline.
 *
 * This sets up the overall `updateLintBaseline` task to merge the contents of the individual
 * `updateLintBaseline*` tasks.
 */
private fun Project.mergeMultiplatformBaselines() {
  // The final output baseline
  val baseline = project.lintBaseline.get().asFile
  // Baseline outputs for each `updateLintBaseline*` task
  val tmpBaselines = mutableSetOf<File>()
  tasks
    .named { it.startsWith("updateLintBaseline") }
    .configureEach {
      if (this.name != "updateLintBaseline") {
        // Create a temporary baseline for this task (e.g. `updateLintBaselineAndroidMain`)
        val tmpBaseline = File(this.temporaryDir, "lint-baseline.xml")
        tmpBaselines.add(tmpBaseline)
        this.doLast {
          // This task will have written its baseline to the final baseline file. Copy it
          // to the tmpBaseline because the final file may be overwritten by another this.
          tmpBaseline.delete()
          if (baseline.exists()) {
            baseline.copyTo(tmpBaseline)
          }
        }
      } else {
        this.doLast {
          // In the `updateLintBaseline` task, merge the contents of the baselines from
          // the `updateLintBaseline*` tasks
          val tmpBaselines = tmpBaselines.filter { it.exists() }
          when (tmpBaselines.size) {
            0 -> return@doLast
            // Just one baseline, be sure the final baseline reflects it
            1 -> tmpBaselines.single().copyTo(baseline, overwrite = true)
            else -> {
              // Copy the contents of all the baselines to the final baseline file
              if (baseline.exists()) baseline.delete()
              for ((i, tmpBaseline) in tmpBaselines.withIndex()) {
                val contents = tmpBaseline.readLines().toMutableList()
                // Only include `<?xml>` and `<issues>` lines from the first file
                if (i != 0) {
                  contents.removeFirst()
                  contents.removeFirst()
                }
                // Only include `</issues>` line from the last file
                if (i != tmpBaselines.size - 1) {
                  contents.removeLast()
                }
                baseline.appendText(contents.joinToString("\n"))
              }
            }
          }
        }
      }
    }
}

/** Android Lint configuration entry point for non-Android projects. */
private fun Project.configureNonAndroidProjectForLint() = afterEvaluate {
  // For Android projects, the Android Gradle Plugin is responsible for applying the lint plugin;
  // however, we need to apply it ourselves for non-Android projects.
  apply(mapOf("plugin" to "com.android.lint"))

  // For Android projects, we can run lint configuration last using `DslLifecycle.finalizeDsl`;
  // however, we need to run it using `Project.afterEvaluate` for non-Android projects.
  configureLint(project.extensions.getByType(), isApp = false)
}

private fun Project.configureLint(lint: Lint, isApp: Boolean) {
  lint.apply {
    abortOnError = isApp
    baseline = lintBaseline.get().asFile
    checkAllWarnings = true
    checkDependencies = true
    checkReleaseBuilds = true
    checkTestSources = true
    explainIssues = true
    ignoreWarnings = false
    noLines = false
    showAll = true
    warningsAsErrors = true
    disable +=
      listOf(
        // Handled by Renovate
        "AndroidGradlePluginVersion",
        "GradleDependency",
        "NewerVersionAvailable",
      )
  }
}
