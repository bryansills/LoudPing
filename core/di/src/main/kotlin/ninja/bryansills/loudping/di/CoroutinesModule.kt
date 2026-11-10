package ninja.bryansills.loudping.di

import dev.zacsweers.metro.Qualifier

@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class ApplicationScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: LoudPingDispatcher)

enum class LoudPingDispatcher {
  Default,
  Io,
}
