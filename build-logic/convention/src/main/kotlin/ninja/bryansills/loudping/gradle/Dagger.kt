package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.id
import ninja.bryansills.loudping.gradle.util.implementation
import ninja.bryansills.loudping.gradle.util.ksp
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureDagger() {
    plugins {
        id("com.google.devtools.ksp")
        id("com.google.dagger.hilt.android")
    }
    dependencies {
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
        ksp(libs.kotlin.metadata)
    }
}
