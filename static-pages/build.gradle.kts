import com.github.gmazzo.buildconfig.BuildConfigExtension
import ninja.bryansills.loudping.gradle.plugin.getSecret
import ninja.bryansills.loudping.gradle.plugin.rootProperties

plugins {
  id("ninja.bryansills.root")
  alias(libs.plugins.kotlin.jvm)
  application
  alias(libs.plugins.buildconfig)
}

application { mainClass = "ninja.bryansills.loudping.staticpages.MainKt" }

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
  val properties = rootProject.rootProperties("local.properties")

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
