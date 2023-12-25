import com.github.gmazzo.buildconfig.BuildConfigExtension
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    kotlin("jvm")
    application
    alias(libs.plugins.buildconfig)
}

application {
    mainClass = "ninja.bryansills.loudping.staticpages.MainKt"
}

dependencies {
    implementation(project(":sneak"))
    implementation(libs.kotlinx.html)
    implementation(libs.okio)
}

buildConfig {
    packageName("ninja.bryansills.loudping")
    useKotlinOutput { internalVisibility = true }

    loadSecrets()
}

fun BuildConfigExtension.loadSecrets() {
    val properties = Properties()
    val localProperties = File(rootDir, "local.properties")
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    }

    val saltString = properties.getSecret("sneak.salt")
    buildConfigField("String", "Salt", """"$saltString"""")

    val callbackUrl = properties.getSecret("callback.url")
    buildConfigField("String", "CallbackUrl", """"$callbackUrl"""")
}

fun Properties.getSecret(
    propertyName: String,
    environmentName: String = propertyName.replace(".", "_").toUpperCaseAsciiOnly(),
    fallback: String = "INVALID $propertyName"
): String {
    val propertyValue: String? = this.getProperty(propertyName)
    val environmentValue: String? = System.getenv(environmentName)

    return propertyValue ?: environmentValue ?: fallback
}
