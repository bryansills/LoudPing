import com.github.gmazzo.buildconfig.BuildConfigExtension
import ninja.bryansills.loudping.gradle.plugin.getSecret
import ninja.bryansills.loudping.gradle.plugin.rootProperties

plugins {
    id("ninja.bryansills.root")
    alias(libs.plugins.kotlin.jvm)
    application
    alias(libs.plugins.buildconfig)
}

application { mainClass = "ninja.bryansills.loudping.deephistory.runner.MainKt" }

dependencies {
    implementation(projects.deepHistoryDash)
    implementation(projects.deepHistory)
    implementation(projects.coroutinesExt)
    implementation(libs.coroutines)
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
