package ninja.bryansills.loudping.gradle

import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import ninja.bryansills.loudping.gradle.util.android
import ninja.bryansills.loudping.gradle.util.androidTestRuntimeOnly
import ninja.bryansills.loudping.gradle.util.coreLibraryDesugaring
import ninja.bryansills.loudping.gradle.util.javaVersion
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.loudPingNamespace
import ninja.bryansills.loudping.gradle.util.toInt
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureAndroidLibrary(shouldEnableKotlin: Boolean = true) {
  android<LibraryExtension> {
    enableKotlin = shouldEnableKotlin

    namespace = loudPingNamespace

    compileSdk { version = release(libs.versions.android.compileSdk.toInt()) }

    defaultConfig {
      minSdk = libs.versions.android.minSdk.toInt()

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
      sourceCompatibility = libs.javaVersion
      targetCompatibility = libs.javaVersion
      isCoreLibraryDesugaringEnabled = true
    }

    // Needed to make Compose Previews happy
    // See https://issuetracker.google.com/issues/567301321 to confirm this is still needed
//    testOptions.unitTests.isIncludeAndroidResources = layout.projectDirectory.dir("src/test").asFile.exists()

    //        androidResources {
    //            // TODO: Do this without doing file IO on configuration
    //            enable = File("src")
    //                    .listFiles()
    //                    ?.any { it.resolve("res").isDirectory }
    //                    ?: false
    //        }
  }

  dependencies {
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    androidTestRuntimeOnly(libs.androidx.test.runner)
  }

  // Needed to make Compose Previews happy
  // See https://issuetracker.google.com/issues/567301321 to confirm this is still needed
//  androidComponents.beforeVariants(androidComponents.selector().withBuildType("release")) { variantBuilder ->
//    (variantBuilder as? HasHostTestsBuilder)?.hostTests?.get(HostTestBuilder.UNIT_TEST_TYPE)?.enable = true
//  }
}

private val Project.androidComponents: LibraryAndroidComponentsExtension
  get() {
    return this.extensions.getByType(LibraryAndroidComponentsExtension::class.java)
  }
