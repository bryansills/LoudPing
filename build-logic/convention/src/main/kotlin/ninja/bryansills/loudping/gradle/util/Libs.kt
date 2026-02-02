package ninja.bryansills.loudping.gradle.util

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.the

internal val Project.libs
    get() = the<LibrariesForLibs>()

internal fun Provider<String>.toInt(): Int {
    return this.get().toInt()
}
