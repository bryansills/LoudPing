plugins {
    id("ninja.bryansills.root")

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.buildconfig) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.dependency.guard) apply false
    alias(libs.plugins.parcelize) apply false
}

dependencyAnalysis {
    // TODO: fix the problems identified by this library
    // issues { all { onAny { severity("fail") } } }
}
