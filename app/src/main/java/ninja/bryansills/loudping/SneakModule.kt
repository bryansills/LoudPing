package ninja.bryansills.loudping

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ninja.bryansills.loudping.app.sneak.BuildSneak
import ninja.bryansills.loudping.app.sneak.RealBuildSneak
import ninja.bryansills.sneak.Sneak

@InstallIn(SingletonComponent::class)
@Module
object SneakModule {

    @Provides
    fun provideSneak(): Sneak = Sneak(BuildConfig.SneakSalt.toByteArray())

    @Provides
    fun provideBuildSneak(sneak: Sneak): BuildSneak {
        return RealBuildSneak(
            sneak = sneak,
            obfuscatedClientId = BuildConfig.SneakClientId.toByteArray(),
            obfuscatedClientSecret = BuildConfig.SneakClientSecret.toByteArray(),
            obfuscatedRedirectUrl = BuildConfig.SneakRedirectUrl.toByteArray(),
            obfuscatedBaseApiUrl = BuildConfig.SneakBaseApiUrl.toByteArray(),
            obfuscatedBaseAuthApiUrl = BuildConfig.SneakBaseAuthApiUrl.toByteArray(),
            obfuscatedAuthorizeUrl = BuildConfig.SneakAuthorizeUrl.toByteArray(),
        )
    }
}
