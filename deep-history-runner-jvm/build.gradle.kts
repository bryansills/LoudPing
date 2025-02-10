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
    mainClass = "ninja.bryansills.loudping.deephistory.runner.MainKt"
}

dependencies {
    implementation(projects.deepHistory)
    implementation(projects.albumRepo)
    implementation(projects.coroutinesExt)
    implementation(projects.database)
    implementation(projects.historyRecorder)
    implementation(projects.network)
    implementation(projects.networkAuth)
    implementation(projects.session)
    implementation(projects.sneak)
    implementation(projects.sneakNetwork)
    implementation(projects.storage)
    implementation(projects.time)
    implementation(projects.trackRepo)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.eithernet)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.okio)
    implementation(libs.kotlinx.serialization.runtime)
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
