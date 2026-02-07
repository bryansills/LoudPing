import ninja.bryansills.loudping.gradle.buildConfigString
import ninja.bryansills.loudping.gradle.getSecret
import ninja.bryansills.loudping.gradle.rootProperties

plugins {
    alias(libs.plugins.loudping.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.bugsnag)
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

        val rootSecrets = rootProject.rootProperties("secrets.properties")
        buildConfigString(
            "SneakSalt",
            rootSecrets.getSecret("sneak.salt"),
        )
        buildConfigString(
            "SneakClientId",
            rootSecrets.getSecret("sneak.clientid"),
        )
        buildConfigString(
            "SneakClientSecret",
            rootSecrets.getSecret("sneak.clientsecret"),
        )
        buildConfigString(
            "SneakRedirectUrl",
            rootSecrets.getSecret("sneak.redirecturl"),
        )
        buildConfigString(
            "SneakBaseApiUrl",
            rootSecrets.getSecret("sneak.baseapiurl"),
        )
        buildConfigString(
            "SneakBaseAuthApiUrl",
            rootSecrets.getSecret("sneak.baseauthapiurl"),
        )
        buildConfigString(
            "SneakAuthorizeUrl",
            rootSecrets.getSecret("sneak.authorizeurl"),
        )

        manifestPlaceholders["BUGSNAG_API_KEY"] = rootSecrets.getSecret("bugsnag.api.key")
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("release/app-debug.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        create("release") {
            if (rootProject.file("release/loudping-signing.jks").exists()) {
                storeFile = rootProject.file("release/loudping-signing.jks")

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
    implementation(projects.appCore)
    implementation(projects.di)
    implementation(projects.database)
    implementation(projects.historyRecorder)
    implementation(projects.network)
    implementation(projects.networkAuth)
    implementation(projects.session)
    implementation(projects.sneakNetwork)
    implementation(projects.sneak)
    implementation(projects.storage)
    implementation(projects.time)
    implementation(projects.logger)
    implementation(projects.loggerBugsnag)

    implementation(libs.androidx.compose.activity)
    implementation(libs.activity.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.runtime.json)
    implementation(libs.okhttp)
    implementation(libs.eithernet.core)
    implementation(libs.eithernet.retrofit)

    implementation(libs.workmanager)
    implementation(libs.workmanager.hilt)

    implementation(libs.bugsnag)
}
