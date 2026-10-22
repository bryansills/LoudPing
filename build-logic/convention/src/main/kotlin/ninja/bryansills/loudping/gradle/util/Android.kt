package ninja.bryansills.loudping.gradle.util

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import java.io.File
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal inline fun <reified AndroidExtension : CommonExtension> Project.android(
  action: AndroidExtension.() -> Unit
) {
  extensions.getByType(CommonExtension::class).apply {
    (this as? AndroidExtension)?.action()
      ?: throw RuntimeException(
        "Tried to configure ${AndroidExtension::class.qualifiedName}, but was actually ${this::class.qualifiedName}?"
      )
  }
}

internal val Project.lintBaseline: RegularFileProperty
  get() = project.objects.fileProperty().fileValue(File(projectDir, "lint-baseline.xml"))

internal val Project.multiplatformExtension
  get() = extensions.findByType(KotlinMultiplatformExtension::class.java)

internal val Project.agpKmpExtension: KotlinMultiplatformAndroidLibraryTarget
  get() {
    return (multiplatformExtension as ExtensionAware)
      .extensions
      .getByType(KotlinMultiplatformAndroidLibraryTarget::class.java)
  }
