package ninja.bryansills.loudping.html.core

import okio.Path.Companion.toPath

fun ProvidesHtmlScope.provideRoot() {
    fileSystem.createDirectories("assets/css".buildPath())
    copyResource(
        sourcePath = "global.css".toPath(),
        destinationPath = "assets/css/global.css".buildPath()
    )

    fileSystem.createDirectories("assets/image".buildPath())
    copyResource(
        sourcePath = "cricket.ico".toPath(),
        destinationPath = "assets/image/cricket.ico".buildPath()
    )
    copyResource(
        sourcePath = "cricket.svg".toPath(),
        destinationPath = "assets/image/cricket.svg".buildPath()
    )
}
