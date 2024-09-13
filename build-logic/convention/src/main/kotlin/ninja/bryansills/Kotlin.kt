package ninja.bryansills

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun Project.configureKotlin() {
    // Configure Java to use our chosen language level. Kotlin will automatically pick this up
    configureJava()
}

internal fun Project.configureKotlinMultiplatform() {
    plugins {
        id("org.jetbrains.kotlin.multiplatform")
    }

    kotlin {
        applyDefaultHierarchyTemplate()
        jvm()
        // TODO: do i want this? it might make sense for all kmp modules to just target the plain jvm...
        if (pluginManager.hasPlugin("com.android.library")) {
            androidTarget()
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
                implementation(libs.findLibrary("coroutines").get())
            }
            commonTest.dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

private fun Project.kotlin(block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure<KotlinMultiplatformExtension>(block)
}

private fun KotlinMultiplatformExtension.sourceSets(
    configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>
): Unit = (this as ExtensionAware).extensions.configure("sourceSets", configure)
