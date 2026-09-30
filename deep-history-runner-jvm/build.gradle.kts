
import com.github.gmazzo.buildconfig.BuildConfigExtension

plugins {
    alias(libs.plugins.loudping.jvm)
    application
    alias(libs.plugins.buildconfig)
}

application {
    mainClass = "ninja.bryansills.loudping.deephistory.runner.MainKt"
}

dependencies {
    implementation(projects.deepHistoryDash)
    implementation(projects.deepHistory)
    implementation(projects.database.core)
    implementation(projects.database.jvm)
    implementation(projects.coroutinesExt)
    implementation(libs.coroutines)
}

buildConfig {
    packageName("ninja.bryansills.loudping.jvm.network.runner")
    useKotlinOutput { internalVisibility = true }

//  val rootSecrets = rootProject.rootProperties("secrets.properties")
//
//  string("JvmRefreshToken", rootSecrets.getSecret("jvm.refresh.token"))
//  string("SneakSalt", rootSecrets.getSecret("sneak.salt"))
//  string("SneakClientId", rootSecrets.getSecret("sneak.clientid"))
//  string("SneakClientSecret", rootSecrets.getSecret("sneak.clientsecret"))
//  string("SneakRedirectUrl", rootSecrets.getSecret("sneak.redirecturl"))
//  string("SneakBaseApiUrl", rootSecrets.getSecret("sneak.baseapiurl"))
//  string("SneakBaseAuthApiUrl", rootSecrets.getSecret("sneak.baseauthapiurl"))
//  string("SneakAuthorizeUrl", rootSecrets.getSecret("sneak.authorizeurl"))

    string("JvmRefreshToken", "jvm.refresh.token")
    string("SneakSalt", "sneak.salt")
    string("SneakClientId", "sneak.clientid")
    string("SneakClientSecret", "sneak.clientsecret")
    string("SneakRedirectUrl", "sneak.redirecturl")
    string("SneakBaseApiUrl", "sneak.baseapiurl")
    string("SneakBaseAuthApiUrl", "sneak.baseauthapiurl")
    string("SneakAuthorizeUrl", "sneak.authorizeurl")
}

fun BuildConfigExtension.string(key: String, value: String) {
    this.buildConfigField("String", key, "\"$value\"")
}
