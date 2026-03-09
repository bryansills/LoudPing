package ninja.bryansills.loudping.html.core

import kotlinx.html.HEAD
import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.lang
import kotlinx.html.link
import kotlinx.html.stream.appendHTML

fun buildHtml(block: HTML.() -> Unit): String = buildString {
    appendLine("<!DOCTYPE html>")
    appendHTML().html {
        lang = "en"
        block()
    }
}

fun HEAD.commonHeadAttributes() {
    link {
        rel = "icon"
        href = "/assets/image/cricket.ico"
        type = "image/x-icon"
    }
}
