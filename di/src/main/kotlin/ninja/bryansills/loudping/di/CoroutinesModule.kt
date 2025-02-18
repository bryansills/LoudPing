package ninja.bryansills.loudping.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME) @Qualifier annotation class ApplicationScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: LoudPingDispatcher)

enum class LoudPingDispatcher {
    Default,
    Io,
}
