package ninja.bryansills.loudping.html.core

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.lang
import kotlinx.html.stream.appendHTML

fun buildHtml(block: HTML.() -> Unit): String = buildString {
    appendLine("<!DOCTYPE html>")
    appendHTML().html {
        lang = "en"
        block()
    }
}
