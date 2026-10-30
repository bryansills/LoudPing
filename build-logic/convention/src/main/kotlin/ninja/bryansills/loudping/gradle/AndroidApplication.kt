package ninja.bryansills.loudping.gradle

import com.android.build.api.dsl.ApplicationExtension
import ninja.bryansills.loudping.gradle.util.android
import ninja.bryansills.loudping.gradle.util.androidTestRuntimeOnly
import ninja.bryansills.loudping.gradle.util.coreLibraryDesugaring
import ninja.bryansills.loudping.gradle.util.javaVersion
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.toInt
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidApplication() {
  android<ApplicationExtension> {
    compileSdk { version = release(libs.versions.android.compileSdk.toInt()) }

    defaultConfig {
      minSdk = libs.versions.android.minSdk.toInt()
      targetSdk = libs.versions.android.targetSdk.toInt()
    }

    compileOptions {
      sourceCompatibility = libs.javaVersion
      targetCompatibility = libs.javaVersion
      isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
      resValues = false
      shaders = false
    }
  }

  dependencies {
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    androidTestRuntimeOnly(libs.androidx.test.runner)
  }
}
