package ninja.bryansills.loudping.gradle.util

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

internal inline fun <reified AndroidExtension : CommonExtension> Project.android(action: AndroidExtension.() -> Unit) {
    extensions.getByType(CommonExtension::class).apply {
        (this as? AndroidExtension)?.action() ?: throw RuntimeException("Tried to configure ${AndroidExtension::class.qualifiedName}, but was actually ${this::class.qualifiedName}?")
    }
}
