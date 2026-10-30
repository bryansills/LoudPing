package ninja.bryansills.loudping.gradle

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import ninja.bryansills.loudping.gradle.util.jvmTarget
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.loudPingNamespace
import ninja.bryansills.loudping.gradle.util.toInt
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

context(project: Project)
internal fun KotlinMultiplatformAndroidLibraryTarget.configureAndroidMultiplatform(
  libs: LibrariesForLibs = project.libs,
) {
  namespace = project.loudPingNamespace

  compileSdk = libs.versions.android.compileSdk.toInt()
  minSdk = libs.versions.android.minSdk.toInt()
  enableCoreLibraryDesugaring = true

  compilerOptions { jvmTarget.set(libs.jvmTarget) }
}
