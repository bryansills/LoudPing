package ninja.bryansills.loudping.staticpages

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.lang
import kotlinx.html.stream.appendHTML
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main() {
    val fs = FileSystem.SYSTEM

    fs.createDirectories("build/html".toPath())
    fs.createDirectories("build/html/callback".toPath())

    val callbackText = callbackPage()
    fs.sink("build/html/callback/index.html".toPath()).buffer().use { sink ->
        sink.writeUtf8(callbackText)
    }
}

fun buildHtml(block: HTML.() -> Unit): String = buildString {
    appendLine("<!DOCTYPE html>")
    appendHTML().html {
        lang = "en"
        block()
    }
}
