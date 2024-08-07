import com.github.gmazzo.buildconfig.BuildConfigExtension
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly

plugins {
    id("ninja-bryansills-spotless")
    kotlin("jvm")
    application
    alias(libs.plugins.buildconfig)
}

application {
    mainClass = "ninja.bryansills.loudping.staticpages.MainKt"
}

dependencies {
    implementation(projects.sneak)
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

    val tokenUrl = properties.getSecret("token.url")
    buildConfigField("String", "TokenUrl", """"$tokenUrl"""")

    val clientId = properties.getSecret("client.id")
    buildConfigField("String", "ClientId", """"$clientId"""")

    val clientOther = properties.getSecret("client.other")
    buildConfigField("String", "ClientOther", """"$clientOther"""")

    val redirectUrl = properties.getSecret("redirect.url")
    buildConfigField("String", "RedirectUrl", """"$redirectUrl"""")

    val startUrl = properties.getSecret("start.url")
    buildConfigField("String", "StartUrl", """"$startUrl"""")
}

/**
 * Copied in a few places...
 */
fun Properties.getSecret(
    propertyName: String,
    environmentName: String = propertyName.replace(".", "_").toUpperCaseAsciiOnly(),
    fallback: String = "INVALID $propertyName",
): String {
    val propertyValue: String? = this.getProperty(propertyName)
    val environmentValue: String? = System.getenv(environmentName)

    return propertyValue ?: environmentValue ?: fallback
}
