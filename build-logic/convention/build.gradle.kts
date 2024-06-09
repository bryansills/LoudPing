import org.gradle.accessors.dm.LibrariesForLibs

val LibrariesForLibs.javaVersion
    get(): JavaVersion = JavaVersion.toVersion(versions.java.version.get())

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
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
}

fun plugin(provider: Provider<PluginDependency>) = with(provider.get()) {
    "$pluginId:$pluginId.gradle.plugin:$version"
}
