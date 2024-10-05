package ninja.bryansills

import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryBuildFeatures
import com.android.build.gradle.BaseExtension
import java.io.File
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid() {
    android {
        compileSdkVersion(libs.findVersionNumber("compile-sdk"))
        defaultConfig {
            minSdk = libs.findVersionNumber("min-sdk")
            targetSdk = libs.findVersionNumber("target-sdk")

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
        }
        buildFeatures {
            resValues = false
            shaders = false

            if (this is LibraryBuildFeatures) {
                androidResources = File("src")
                    .listFiles()
                    ?.any { it.resolve("res").isDirectory }
                    ?: false
            }
        }
    }
    dependencies {
        coreLibraryDesugaring(libs["android-desugarJdkLibs"])
        androidTestRuntimeOnly(libs["androidx-test-runner"])
    }
}

private fun Project.android(action: BaseExtension.() -> Unit) = extensions.configure<BaseExtension>(action)

private fun BaseExtension.buildFeatures(action: BuildFeatures.() -> Unit) {
    if (this is CommonExtension<*,*,*,*,*,*>) {
        buildFeatures(action)
    } else {
        throw IllegalStateException("This should work...")
    }
}
