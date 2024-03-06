import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly

plugins {
    id("ninja-bryansills-compose-android-app")
}

android {
    namespace = "ninja.bryansills.loudping"

    defaultConfig {
        applicationId = "ninja.bryansills.loudping"

        val rootLocalProperties = rootProject.rootProperties("local.properties")
        val appVersionName =
            rootLocalProperties.getSecret(
                "version.name",
                fallback = "0.0.69",
            )
        val appVersionParts = appVersionName.split(".").map { it.toInt() }
        val appVersionCode = (10_000 * appVersionParts[0]) + (100 * appVersionParts[1]) + appVersionParts[2]

        versionCode = appVersionCode
        versionName = appVersionName

        buildConfigField(
            "String",
            "SneakSalt",
            rootLocalProperties.getSecret("sneak.salt").toBuildConfig(),
        )
        buildConfigField(
            "String",
            "SneakClientId",
            rootLocalProperties.getSecret("sneak.clientid").toBuildConfig(),
        )
        buildConfigField(
            "String",
            "SneakClientSecret",
            rootLocalProperties.getSecret("sneak.clientsecret").toBuildConfig(),
        )
        buildConfigField(
            "String",
            "SneakRedirectUrl",
            rootLocalProperties.getSecret("sneak.redirecturl").toBuildConfig(),
        )
        buildConfigField(
            "String",
            "SneakBaseApiUrl",
            rootLocalProperties.getSecret("sneak.baseapiurl").toBuildConfig(),
        )
        buildConfigField(
            "String",
            "SneakBaseAuthApiUrl",
            rootLocalProperties.getSecret("sneak.baseauthapiurl").toBuildConfig(),
        )
        buildConfigField(
            "String",
            "SneakAuthorizeUrl",
            rootLocalProperties.getSecret("sneak.authorizeurl").toBuildConfig(),
        )
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("release/app-debug.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        create("release") {
            if (rootProject.file("release/loudping-upload.jks").exists()) {
                storeFile = rootProject.file("release/loudping-upload.jks")

                val keystoreProperties = rootProject.rootProperties("release/keystore.properties")

                storePassword = keystoreProperties.getSecret("release.store.password")
                keyAlias = keystoreProperties.getSecret("release.key.alias")
                keyPassword = keystoreProperties.getSecret("release.key.password")
            }
        }
    }

    buildTypes {
        val release =
            getByName("release") {
                signingConfig = signingConfigs.findByName("release")
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                isMinifyEnabled = true
            }

        create("benchmark") {
            initWith(release)
            matchingFallbacks.add("release")

            signingConfig = signingConfigs.getByName("debug")
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":app-core"))
    implementation(project(":app-sneak"))
    implementation(project(":sneak"))
    implementation(libs.androidx.compose.activity)
    implementation(libs.activity.ktx)
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

fun String.toBuildConfig(): String {
    return "\"$this\""
}
