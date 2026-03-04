package ninja.bryansills.loudping.html.core

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath as okioToPath

interface ProvidesHtmlScope {
    val fileSystem: FileSystem

    val basePath: String

    fun String.toPath(): Path = "$basePath/$this".okioToPath()
}

class DefaultProvidesHtmlScope(
    override val fileSystem: FileSystem = FileSystem.SYSTEM,
    override val basePath: String,
) : ProvidesHtmlScope
