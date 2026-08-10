package ninja.bryansills.loudping.html.digest

import ninja.bryansills.loudping.html.core.ProvidesHtmlScope
import ninja.bryansills.loudping.time.TimeProvider
import okio.buffer

suspend fun ProvidesHtmlScope.provideDigest(
  digestBuilder: DigestBuilder,
  timeProvider: TimeProvider,
) {
  fileSystem.createDirectories("digest".buildPath())
  val dailyPage = generateDigest(timeProvider.now, digestBuilder.build())
  fileSystem.sink("digest/index.html".buildPath()).buffer().use { sink ->
    sink.writeUtf8(dailyPage)
  }
}
