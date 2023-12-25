package ninja.bryansills.loudping.staticpages

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.lang
import kotlinx.html.stream.appendHTML
import ninja.bryansills.loudping.BuildConfig
import ninja.bryansills.sneak.Sneak
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main() {
    val fs = FileSystem.SYSTEM

    val sneak = Sneak(BuildConfig.Salt.toByteArray())

    fs.createDirectories("build/html".toPath())
    fs.createDirectories("build/html/callback".toPath())
    fs.createDirectories("build/html/start".toPath())

    val callbackText = CallbackPage(
        sneak = sneak,
        saltText = BuildConfig.Salt,
        callbackUrl = BuildConfig.CallbackUrl,
    )
    fs.sink("build/html/callback/index.html".toPath()).buffer().use { sink ->
        sink.writeUtf8(callbackText)
    }

    val redirectText = RedirectPage()
    fs.sink("build/html/start/index.html".toPath()).buffer().use { sink ->
        sink.writeUtf8(redirectText)
    }
}

fun buildHtml(block: HTML.() -> Unit): String = buildString {
    appendLine("<!DOCTYPE html>")
    appendHTML().html {
        lang = "en"
        block()
    }
}