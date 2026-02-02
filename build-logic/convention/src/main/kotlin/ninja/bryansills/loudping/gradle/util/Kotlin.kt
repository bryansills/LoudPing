package ninja.bryansills.loudping.gradle.util

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal inline fun <reified T : KotlinProjectExtension> Project.kotlin(noinline block: T.() -> Unit) {
    extensions.configure<T>(block)
}

internal fun KotlinMultiplatformExtension.sourceSets(
    configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>,
): Unit = (this as ExtensionAware).extensions.configure("sourceSets", configure)

internal fun Project.androidLibrary(block: KotlinMultiplatformAndroidLibraryTarget.() -> Unit) {
    kotlin<KotlinMultiplatformExtension> {
        extensions.configure<KotlinMultiplatformAndroidLibraryTarget>(block)
    }
}
