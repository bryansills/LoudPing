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
    mainClass = "ninja.bryansills.loudping.generateappsecrets.MainKt"
}

dependencies {
    implementation(project(":sneak"))
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

    val saltString = properties.getSecret("raw.sneak.salt")
    buildConfigField("String", "Salt", """"$saltString"""")

    val clientId = properties.getSecret("raw.clientid")
    buildConfigField("String", "ClientId", """"$clientId"""")

    val clientSecret = properties.getSecret("raw.clientsecret")
    buildConfigField("String", "ClientSecret", """"$clientSecret"""")

    val redirectUrl = properties.getSecret("raw.redirecturl")
    buildConfigField("String", "RedirectUrl", """"$redirectUrl"""")

    val baseApiUrl = properties.getSecret("raw.baseapiurl")
    buildConfigField("String", "BaseApiUrl", """"$baseApiUrl"""")

    val baseAuthApiUrl = properties.getSecret("raw.baseauthapiurl")
    buildConfigField("String", "BaseAuthApiUrl", """"$baseAuthApiUrl"""")

    val authorizeUrl = properties.getSecret("raw.authorizeurl")
    buildConfigField("String", "AuthorizeUrl", """"$authorizeUrl"""")
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
