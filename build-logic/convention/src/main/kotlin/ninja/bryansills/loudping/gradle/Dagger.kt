package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.alias
import ninja.bryansills.loudping.gradle.util.implementation
import ninja.bryansills.loudping.gradle.util.ksp
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureDagger() {
    plugins {
        alias(libs.plugins.ksp)
        alias(libs.plugins.hilt.plugin)
    }
    dependencies {
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
        ksp(libs.kotlin.metadata)
    }
}
