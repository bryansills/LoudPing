package ninja.bryansills.loudping.gradle

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.android.build.api.dsl.LibraryExtension
import ninja.bryansills.loudping.gradle.util.android
import ninja.bryansills.loudping.gradle.util.androidTestRuntimeOnly
import ninja.bryansills.loudping.gradle.util.coreLibraryDesugaring
import ninja.bryansills.loudping.gradle.util.javaVersion
import ninja.bryansills.loudping.gradle.util.jvmTarget
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.toInt
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidApplication() {
    android<ApplicationExtension> {
        compileSdk { version = release(libs.versions.android.compileSdk.toInt()) }

        defaultConfig {
            minSdk = libs.versions.android.minSdk.toInt()
            targetSdk = libs.versions.android.targetSdk.toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

internal fun Project.configureAndroidLibrary(shouldEnableKotlin: Boolean = true) {
    android<LibraryExtension> {
        enableKotlin = shouldEnableKotlin

        namespace = loudPingNamespace

        compileSdk {
            version = release(libs.versions.android.compileSdk.toInt())
        }

        defaultConfig {
            minSdk = libs.versions.android.minSdk.toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = libs.javaVersion
            targetCompatibility = libs.javaVersion
            isCoreLibraryDesugaringEnabled = true
        }

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
}

internal fun KotlinMultiplatformAndroidLibraryTarget.configureAndroidMultiplatform(
    project: Project,
    libs: LibrariesForLibs = project.libs,
) {
    namespace = project.loudPingNamespace

    compileSdk = libs.versions.android.compileSdk.toInt()
    minSdk = libs.versions.android.minSdk.toInt()
    enableCoreLibraryDesugaring = true

    compilerOptions {
        jvmTarget.set(libs.jvmTarget)
    }
}

private val Project.loudPingNamespace: String
    get() {
        return "ninja.bryansills.loudping" + project.path.replace(":", ".").replace("-", "_")
    }
