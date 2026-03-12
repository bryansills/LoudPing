package ninja.bryansills.loudping.html.core

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath as okioToPath
import okio.buffer

interface ProvidesHtmlScope {
    val fileSystem: FileSystem

    val resources: FileSystem

    val basePath: String

    fun String.buildPath(): Path = "$basePath/$this".okioToPath()
}

class DefaultProvidesHtmlScope(
    override val fileSystem: FileSystem = FileSystem.SYSTEM,
    override val resources: FileSystem = FileSystem.RESOURCES,
    override val basePath: String,
) : ProvidesHtmlScope

/**
 * This is kinda dumb (I mean `cp source destination` works just fine), but it is nice to have all
 * the packaging of the HTML in code.
 */
fun ProvidesHtmlScope.copyResource(
    source: Path,
    target: Path,
) {
    resources.source(source).use { bytesIn ->
        fileSystem.sink(target).buffer().use { bytesOut ->
            bytesOut.writeAll(bytesIn)
        }
    }
}
