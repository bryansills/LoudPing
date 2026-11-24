import ninja.bryansills.loudping.gradle.util.addString
import ninja.bryansills.loudping.gradle.util.getSecret
import ninja.bryansills.loudping.gradle.util.loadProperties

plugins {
  alias(libs.plugins.loudping.jvm)
  alias(libs.plugins.metro)
  alias(libs.plugins.buildconfig)
}

dependencies {
  api(libs.metro.runtime)
  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotlin.test.junit)
}

buildConfig {
  packageName("ninja.bryansills.loudping.sneak")

  val secrets = loadProperties("secrets.properties")

  addString("JvmRefreshToken", secrets.getSecret("jvm.refresh.token"))
  addString("Salt", secrets.getSecret("sneak.salt"))
  addString("SneakClientId", secrets.getSecret("sneak.clientid"))
  addString("SneakClientSecret", secrets.getSecret("sneak.clientsecret"))
  addString("SneakRedirectUrl", secrets.getSecret("sneak.redirecturl"))
  addString("SneakBaseApiUrl", secrets.getSecret("sneak.baseapiurl"))
  addString("SneakBaseAuthApiUrl", secrets.getSecret("sneak.baseauthapiurl"))
  addString("SneakAuthorizeUrl", secrets.getSecret("sneak.authorizeurl"))
}
