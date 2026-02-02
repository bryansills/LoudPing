package ninja.bryansills.loudping.gradle

import ninja.bryansills.loudping.gradle.util.java
import ninja.bryansills.loudping.gradle.util.libs
import ninja.bryansills.loudping.gradle.util.toInt
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion

internal fun Project.configureJava() {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(libs.versions.java.version.toInt()))
        }
    }
}
