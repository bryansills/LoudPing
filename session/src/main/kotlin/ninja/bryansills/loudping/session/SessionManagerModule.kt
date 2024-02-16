package ninja.bryansills.loudping.session

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SessionManagerModule {
    @Singleton
    @Binds
    fun bindSessionManager(realSessionManager: RealSessionManager): SessionManager
}
