package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.androidLibrary
import ninja.bryansills.loudping.gradle.util.coreLibraryDesugaring
import ninja.bryansills.loudping.gradle.util.id
import ninja.bryansills.loudping.gradle.util.java
import ninja.bryansills.loudping.gradle.util.kotlin
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import ninja.bryansills.loudping.gradle.util.sourceSets
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinJvmOnly() {
    java {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.version.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.version.get())
    }

    kotlin<KotlinJvmProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(libs.versions.java.version.get()))
        }
    }
}
internal fun Project.configureKotlinAndroid() {
    kotlin<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(libs.versions.java.version.get()))
        }
    }
}

internal fun Project.configureKotlinMultiplatform() {
    plugins {
        id("org.jetbrains.kotlin.multiplatform")
        id("com.android.kotlin.multiplatform.library")
    }

    kotlin<KotlinMultiplatformExtension> {
        jvm {
            compilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(libs.versions.java.version.get()))
            }
        }
        androidLibrary {
            configureAndroidMultiplatform(this@configureKotlinMultiplatform)
        }

        targets.configureEach {
            compilations.configureEach {
                compileTaskProvider.configure {
                    compilerOptions {
                        freeCompilerArgs.add("-Xexpect-actual-classes")
                    }
                }
            }
        }

        sourceSets {
            commonMain.dependencies {
                implementation(libs.coroutines)
            }
            commonTest.dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }

    // Have to apply desugaring here for some reason...
    dependencies {
        coreLibraryDesugaring(libs.android.desugarJdkLibs)
    }
}
