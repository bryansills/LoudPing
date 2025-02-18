@file:OptIn(ExperimentalStdlibApi::class)

package ninja.bryansills.loudping.generateappsecrets

import ninja.bryansills.loudping.BuildConfig
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
            "sneak.clientid" to sneak.toObfuscatedString(BuildConfig.ClientId),
            "sneak.clientsecret" to sneak.toObfuscatedString(BuildConfig.ClientSecret),
            "sneak.redirecturl" to sneak.toObfuscatedString(BuildConfig.RedirectUrl),
            "sneak.baseapiurl" to sneak.toObfuscatedString(BuildConfig.BaseApiUrl),
            "sneak.baseauthapiurl" to sneak.toObfuscatedString(BuildConfig.BaseAuthApiUrl),
            "sneak.authorizeurl" to sneak.toObfuscatedString(BuildConfig.AuthorizeUrl),
        )

    val fileText = rawData.joinToString(separator = "\n") { (key, value) -> "$key=$value" }

    fs.sink("../secrets.properties".toPath()).buffer().use { sink -> sink.writeUtf8(fileText) }
}

fun Sneak.toObfuscatedString(raw: String): String {
    return this.obfuscate(raw).toHexString()
}
