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
            id = "ninja.bryansills.root"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.RootConventionPlugin"
        }
        register("androidApp") {
            id = "ninja.bryansills.android.app"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.AndroidAppConventionPlugin"
        }
        register("javaAndroidLibrary") {
            id = "ninja.bryansills.android.library"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.JavaAndroidLibraryConventionPlugin"
        }
        register("kotlinAndroidLibrary") {
            id = "ninja.bryansills.android.library.kotlin"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.KotlinAndroidLibraryConventionPlugin"
        }
        register("kotlinAndroidDaggerLibrary") {
            id = "ninja.bryansills.android.library.kotlin.dagger"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.KotlinAndroidDaggerLibraryConventionPlugin"
        }
        register("kotlinMultiplatformLibrary") {
            id = "ninja.bryansills.multiplatform.library"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.KotlinMultiplatformLibraryConventionPlugin"
        }
        register("composeMultiplatformLibrary") {
            id = "ninja.bryansills.multiplatform.library.compose"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.ComposeMultiplatformLibraryConventionPlugin"
        }
        register("composeAndroidScreen") {
            id = "ninja.bryansills.android.library.compose.screen"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.ComposeAndroidScreenConventionPlugin"
        }
        register("composeMosaic") {
            id = "ninja.bryansills.compose.mosaic"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.ComposeMosaicConventionPlugin"
        }
        register("sqldelight") {
            id = "ninja.bryansills.sqldelight"
            implementationClass = "ninja.bryansills.loudping.gradle.plugin.SqldelightConventionPlugin"
        }
    }
}
