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
    }
}
