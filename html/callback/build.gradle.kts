plugins {
    alias(libs.plugins.loudping.jvm)
    application
    alias(libs.plugins.buildconfig)
}

application {
    mainClass = "ninja.bryansills.loudping.html.callback.MainKt"
}

dependencies {
    implementation(projects.sneak)
    implementation(projects.html.core)
    implementation(libs.kotlinx.html)
    implementation(libs.okio)
}

buildConfig {
    packageName("ninja.bryansills.loudping")
    useKotlinOutput { internalVisibility = true }

//    loadSecrets()

    buildConfigField("Salt", """"saltString"""")
    buildConfigField("TokenUrl", """"tokenUrl"""")
    buildConfigField("ClientId", """"clientId"""")
    buildConfigField("ClientOther", """"clientOther"""")
    buildConfigField("RedirectUrl", """"redirectUrl"""")
    buildConfigField("StartUrl", """"startUrl"""")
}

//fun BuildConfigExtension.loadSecrets() {
//    val properties = rootProject.rootProperties("local.properties")
//
//    val saltString = properties.getSecret("sneak.salt")
//    buildConfigField("String", "Salt", """"$saltString"""")
//
//    val tokenUrl = properties.getSecret("token.url")
//    buildConfigField("String", "TokenUrl", """"$tokenUrl"""")
//
//    val clientId = properties.getSecret("client.id")
//    buildConfigField("String", "ClientId", """"$clientId"""")
//
//    val clientOther = properties.getSecret("client.other")
//    buildConfigField("String", "ClientOther", """"$clientOther"""")
//
//    val redirectUrl = properties.getSecret("redirect.url")
//    buildConfigField("String", "RedirectUrl", """"$redirectUrl"""")
//
//    val startUrl = properties.getSecret("start.url")
//    buildConfigField("String", "StartUrl", """"$startUrl"""")
//}
