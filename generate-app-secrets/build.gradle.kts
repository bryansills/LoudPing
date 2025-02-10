import com.github.gmazzo.buildconfig.BuildConfigExtension
import ninja.bryansills.loudping.gradle.plugin.getSecret
import ninja.bryansills.loudping.gradle.plugin.rootProperties

plugins {
    id("ninja.bryansills.root")
    alias(libs.plugins.kotlin.jvm)
    application
    alias(libs.plugins.buildconfig)
}

application {
    mainClass = "ninja.bryansills.loudping.generateappsecrets.MainKt"
}

dependencies {
    implementation(projects.sneak)
    implementation(libs.okio)
}

buildConfig {
    packageName("ninja.bryansills.loudping")
    useKotlinOutput { internalVisibility = true }

    loadSecrets()
}

fun BuildConfigExtension.loadSecrets() {
    val properties = rootProject.rootProperties("local.properties")

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
