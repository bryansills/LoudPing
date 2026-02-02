package ninja.bryansills.loudping.gradle

import com.android.build.gradle.internal.lint.AndroidLintAnalysisTask
import com.android.build.gradle.internal.lint.LintModelWriterTask
import ninja.bryansills.loudping.gradle.util.androidTestImplementation
import ninja.bryansills.loudping.gradle.util.composeCompiler
import ninja.bryansills.loudping.gradle.util.debugImplementation
import ninja.bryansills.loudping.gradle.util.implementation
import ninja.bryansills.loudping.gradle.util.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeCompiler() {
    composeCompiler {
        // Needed for Layout Inspector to be able to see all of the nodes in the component tree:
        // https://issuetracker.google.com/issues/338842143
        includeSourceInformation.set(true)
    }

    // Workaround for:
    // Task 'generateDebugUnitTestLintModel' uses this output of task
    // 'generateResourceAccessorsForAndroidUnitTest' without declaring an explicit or
    // implicit dependency.
    tasks.matching { it is AndroidLintAnalysisTask || it is LintModelWriterTask }.configureEach {
        mustRunAfter(tasks.matching { it.name.startsWith("generateResourceAccessorsFor") })
    }
}

internal fun Project.configureComposeAndroid() {
    configureComposeCompiler()

    dependencies {
        val composeBom = libs.androidx.compose.bom
        implementation(platform(composeBom))
        androidTestImplementation(platform(composeBom))

        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.compose.preview)
        debugImplementation(libs.androidx.compose.ui.tooling)
    }
}
