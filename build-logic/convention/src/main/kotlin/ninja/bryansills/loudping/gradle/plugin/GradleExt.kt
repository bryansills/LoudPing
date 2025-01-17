package ninja.bryansills.loudping.gradle.plugin

import com.android.build.api.dsl.VariantDimension
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties
import org.gradle.api.Project

fun Project.rootProperties(propertiesPath: String): Properties {
    val result = Properties()
    val keystorePropertiesFile = this.rootProject.file(propertiesPath)
    if (keystorePropertiesFile.isFile) {
        InputStreamReader(FileInputStream(keystorePropertiesFile), Charsets.UTF_8).use { reader ->
            result.load(reader)
        }
    }
    return result
}

fun Properties.getSecret(
    propertyName: String,
    environmentName: String = propertyName.replace(".", "_").uppercase(),
    fallback: String = "INVALID $propertyName",
): String {
    val propertyValue: String? = this.getProperty(propertyName)
    val environmentValue: String? = System.getenv(environmentName)

    return propertyValue ?: environmentValue ?: fallback
}

fun VariantDimension.buildConfigString(key: String, value: String) {
    this.buildConfigField("String", key, "\"$value\"")
}
