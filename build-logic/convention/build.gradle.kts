import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.java.version.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.java.version.get())
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(libs.versions.java.version.get())
        allWarningsAsErrors = true
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    // needed so we can use `libs.[WHATEVER]` in this module
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    // needed because Gradle runs with an old version of Kotlin
    compileOnly(platform(libs.kotlin.gradle.bom))

    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.gradlePluginApi)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.dependencyGuard.gradlePlugin)
    compileOnly(libs.dependencyAnalysis.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("root") {
            id = libs.plugins.loudping.root.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.RootConventionPlugin"
        }
        register("androidApp") {
            id = libs.plugins.loudping.android.application.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.AndroidAppConventionPlugin"
        }
        register("javaAndroidLibrary") {
            id = libs.plugins.loudping.android.library.java.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.JavaAndroidLibraryConventionPlugin"
        }
        register("kotlinAndroidLibrary") {
            id = libs.plugins.loudping.android.library.kotlin.plain.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.KotlinAndroidLibraryConventionPlugin"
        }
        register("kotlinAndroidDaggerLibrary") {
            id = libs.plugins.loudping.android.library.kotlin.dagger.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.KotlinAndroidDaggerLibraryConventionPlugin"
        }
        register("composeAndroidScreen") {
            id = libs.plugins.loudping.android.library.compose.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.ComposeAndroidScreenConventionPlugin"
        }
        register("kotlinJvm") {
            id = libs.plugins.loudping.jvm.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.KotlinJvmLibraryConventionPlugin"
        }
        register("kotlinMultiplatformLibrary") {
            id = libs.plugins.loudping.multiplatform.plain.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.KotlinMultiplatformLibraryConventionPlugin"
        }
        register("composeMultiplatformLibrary") {
            id = libs.plugins.loudping.multiplatform.compose.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.ComposeMultiplatformLibraryConventionPlugin"
        }
        register("composeMosaic") {
            id = libs.plugins.loudping.mosaic.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.ComposeMosaicConventionPlugin"
        }
        register("sqldelight") {
            id = libs.plugins.loudping.sqldelight.get().pluginId
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.SqldelightConventionPlugin"
        }
    }
}
