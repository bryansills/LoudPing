package ninja.bryansills

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

internal fun Project.configureJava() {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(libs.javaVersion.majorVersion))
        }
    }
}

private fun Project.java(action: JavaPluginExtension.() -> Unit) = extensions.configure<JavaPluginExtension>(action)
