import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins` // todo: delete
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.samReceiver)
    alias(libs.plugins.spotless)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.javaVersion.majorVersion))
    }
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktlint(libs.versions.ktlint.get())
    }

    kotlinGradle {
        target("*.kts")
        ktlint(libs.versions.ktlint.get())
    }
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(plugin(libs.plugins.kotlin.android))
    implementation(plugin(libs.plugins.compose.compiler))
    implementation(plugin(libs.plugins.android.application))
    implementation(plugin(libs.plugins.android.library))
    implementation(plugin(libs.plugins.spotless))
    implementation(plugin(libs.plugins.ksp))
    implementation(plugin(libs.plugins.hilt.plugin))
    implementation(plugin(libs.plugins.kotlinx.serialization))
    implementation(plugin(libs.plugins.dependency.guard))
    implementation(plugin(libs.plugins.dependency.analysis))
}

private fun plugin(provider: Provider<PluginDependency>) = with(provider.get()) {
    "$pluginId:$pluginId.gradle.plugin:$version"
}

private val LibrariesForLibs.javaVersion
    get(): JavaVersion = JavaVersion.toVersion(versions.java.version.get())

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
