@file:OptIn(ExperimentalStdlibApi::class)

package ninja.bryansills.loudping.generateappsecrets

import ninja.bryansills.loudping.raw.BuildConfig
import ninja.bryansills.sneak.Sneak
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main() {
  val fs = FileSystem.SYSTEM
  val sneak = Sneak(BuildConfig.Salt.toByteArray())

  val rawData =
    listOf(
      "sneak.salt" to BuildConfig.Salt,
      "sneak.clientid" to sneak.toObfuscatedString(BuildConfig.RawClientId),
      "sneak.clientsecret" to sneak.toObfuscatedString(BuildConfig.RawClientSecret),
      "sneak.redirecturl" to sneak.toObfuscatedString(BuildConfig.RawRedirectUrl),
      "sneak.baseapiurl" to sneak.toObfuscatedString(BuildConfig.RawBaseApiUrl),
      "sneak.baseauthapiurl" to sneak.toObfuscatedString(BuildConfig.RawBaseAuthApiUrl),
      "sneak.authorizeurl" to sneak.toObfuscatedString(BuildConfig.RawAuthorizeUrl),
    )

  val fileText = rawData.joinToString(separator = "\n") { (key, value) -> "$key=$value" }

  fs.sink("secrets.properties".toPath()).buffer().use { sink -> sink.writeUtf8(fileText) }
}

fun Sneak.toObfuscatedString(raw: String): String = this.obfuscate(raw).toHexString()
