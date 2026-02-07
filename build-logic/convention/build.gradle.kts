import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
//    alias(libs.plugins.kotlin.samReceiver)
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

// samWithReceiver {
//    annotation(HasImplicitReceiver::class.qualifiedName!!)
// }

dependencies {
    // needed so we can use `libs.[WHATEVER]` in this module
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.android.gradlePlugin)
    implementation(libs.android.gradlePluginApi)
    implementation(libs.android.tools.common)
    implementation(plugin(libs.plugins.kotlin.android))
    implementation(plugin(libs.plugins.compose.compiler))
    implementation(plugin(libs.plugins.android.application))
    implementation(plugin(libs.plugins.android.library))
    implementation("com.android.kotlin.multiplatform.library:com.android.kotlin.multiplatform.library.gradle.plugin:9.0.0")
    implementation(plugin(libs.plugins.spotless))
    implementation(plugin(libs.plugins.ksp))
    implementation(plugin(libs.plugins.hilt.plugin))
    implementation(plugin(libs.plugins.kotlinx.serialization))
    implementation(plugin(libs.plugins.dependency.guard))
    implementation(plugin(libs.plugins.dependency.analysis))

    // needed because Gradle runs with an old version of Kotlin
    implementation(platform(libs.kotlin.gradle.bom))
}

private fun plugin(provider: Provider<PluginDependency>) = with(provider.get()) {
    "$pluginId:$pluginId.gradle.plugin:$version"
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
