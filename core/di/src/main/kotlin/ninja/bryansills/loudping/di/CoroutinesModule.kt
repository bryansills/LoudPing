package ninja.bryansills.loudping.di

import dev.zacsweers.metro.Qualifier
import javax.inject.Qualifier as JavaxQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@JavaxQualifier
annotation class ApplicationScope

@Qualifier
@JavaxQualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: LoudPingDispatcher)

enum class LoudPingDispatcher {
  Default,
  Io,
}
