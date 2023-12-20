package ninja.bryansills.loudping.staticpages

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.lang
import kotlinx.html.stream.appendHTML
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main() {
    FileSystem.SYSTEM.createDirectories("build/html".toPath())

    val callbackText = CallbackPage()
    FileSystem.SYSTEM.sink("build/html/callback.html".toPath()).buffer().use { sink ->
        sink.writeUtf8(callbackText)
    }

    val redirectText = RedirectPage()
    FileSystem.SYSTEM.sink("build/html/start.html".toPath()).buffer().use { sink ->
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