package ninja.bryansills

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val VersionCatalog.javaVersion
    get(): JavaVersion = JavaVersion.toVersion(findVersion("java-version").get().requiredVersion)

internal fun VersionCatalog.findVersionNumber(alias: String): Int {
    return findVersion(alias).get().requiredVersion.toInt()
}

internal fun VersionCatalog.findVersionString(alias: String): String {
    return findVersion(alias).get().requiredVersion
}
