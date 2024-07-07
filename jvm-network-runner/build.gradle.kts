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
    mainClass = "ninja.bryansills.loudping.jvm.network.runner.MainKt"
}

dependencies {
    implementation(project(":database"))
    implementation(project(":history-recorder"))
    implementation(project(":network"))
    implementation(project(":network-auth"))
    implementation(project(":session"))
    implementation(project(":sneak"))
    implementation(project(":sneak-network"))
    implementation(project(":storage"))
    implementation(project(":time"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.runtime)
    implementation(libs.okhttp)
}

buildConfig {
    packageName("ninja.bryansills.loudping.jvm.network.runner")
    useKotlinOutput { internalVisibility = true }

    val rootSecrets = rootProject.rootProperties("secrets.properties")

    string("JvmRefreshToken", rootSecrets.getSecret("jvm.refresh.token"))
    string("SneakSalt", rootSecrets.getSecret("sneak.salt"))
    string("SneakClientId", rootSecrets.getSecret("sneak.clientid"))
    string("SneakClientSecret", rootSecrets.getSecret("sneak.clientsecret"))
    string("SneakRedirectUrl", rootSecrets.getSecret("sneak.redirecturl"))
    string("SneakBaseApiUrl", rootSecrets.getSecret("sneak.baseapiurl"))
    string("SneakBaseAuthApiUrl", rootSecrets.getSecret("sneak.baseauthapiurl"))
    string("SneakAuthorizeUrl", rootSecrets.getSecret("sneak.authorizeurl"))
}

fun BuildConfigExtension.string(key: String, value: String) {
    this.buildConfigField("String", key, "\"$value\"")
}

/**
 * Copied in a few places...
 */
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
    environmentName: String = propertyName.replace(".", "_").toUpperCaseAsciiOnly(),
    fallback: String = "INVALID $propertyName",
): String {
    val propertyValue: String? = this.getProperty(propertyName)
    val environmentValue: String? = System.getenv(environmentName)

    return propertyValue ?: environmentValue ?: fallback
}
