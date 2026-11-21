import ninja.bryansills.loudping.gradle.util.addString
import ninja.bryansills.loudping.gradle.util.getSecret
import ninja.bryansills.loudping.gradle.util.loadProperties

plugins {
  alias(libs.plugins.loudping.jvm)
  application
  alias(libs.plugins.buildconfig)
}

application {
  mainClass = "ninja.bryansills.loudping.generateappsecrets.MainKt"
}

dependencies {
  implementation(projects.core.sneak)
  implementation(libs.okio)
}

buildConfig {
  packageName("ninja.bryansills.loudping.raw")

  val secrets = loadProperties("secrets.properties")

  addString("Salt", secrets.getSecret("raw.salt"))
  addString("RawClientId", secrets.getSecret("raw.clientId"))
  addString("RawClientSecret", secrets.getSecret("raw.clientSecret"))
  addString("RawRedirectUrl", secrets.getSecret("raw.redirectUrl"))
  addString("RawBaseApiUrl", secrets.getSecret("raw.baseApiUrl"))
  addString("RawBaseAuthApiUrl", secrets.getSecret("raw.baseAuthApiUrl"))
  addString("RawAuthorizeUrl", secrets.getSecret("raw.authorizeUrl"))
}
