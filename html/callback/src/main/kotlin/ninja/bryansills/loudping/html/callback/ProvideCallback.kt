package ninja.bryansills.loudping.html.callback

import ninja.bryansills.loudping.html.core.ProvidesHtmlScope
import okio.buffer

fun ProvidesHtmlScope.provideCallback() {
    fileSystem.createDirectories("callback".toPath())

    val callbackText = callbackPage()
    fileSystem.sink("callback/index.html".toPath()).buffer().use { sink ->
        sink.writeUtf8(callbackText)
    }
}
