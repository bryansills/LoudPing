package ninja.bryansills.loudping.gradle.util

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

internal fun Provider<String>.toInt(): Int {
    return this.get().toInt()
}

internal val LibrariesForLibs.jvmTarget: JvmTarget
    get() = JvmTarget.fromTarget(this.versions.java.version.get())

internal val LibrariesForLibs.javaVersion: JavaVersion
    get() = JavaVersion.toVersion(this.versions.java.version.get())
