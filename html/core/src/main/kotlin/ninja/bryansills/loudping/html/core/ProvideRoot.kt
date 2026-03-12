package ninja.bryansills.loudping.html.core

import okio.Path.Companion.toPath

fun ProvidesHtmlScope.provideRoot() {
    fileSystem.createDirectories("assets/css".buildPath())
    copyResource(
        source = "global.css".toPath(),
        target = "assets/css/global.css".buildPath(),
    )

    fileSystem.createDirectories("assets/image".buildPath())
    copyResource(
        source = "cricket.ico".toPath(),
        target = "assets/image/cricket.ico".buildPath(),
    )
    copyResource(
        source = "cricket.svg".toPath(),
        target = "assets/image/cricket.svg".buildPath(),
    )
}
