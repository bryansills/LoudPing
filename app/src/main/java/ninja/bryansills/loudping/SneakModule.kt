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

    @OptIn(ExperimentalStdlibApi::class)
    @Provides
    fun provideBuildSneak(sneak: Sneak): BuildSneak {
        return RealBuildSneak(
            sneak = sneak,
            obfuscatedClientId = BuildConfig.SneakClientId.hexToByteArray(),
            obfuscatedClientSecret = BuildConfig.SneakClientSecret.hexToByteArray(),
            obfuscatedRedirectUrl = BuildConfig.SneakRedirectUrl.hexToByteArray(),
            obfuscatedBaseApiUrl = BuildConfig.SneakBaseApiUrl.hexToByteArray(),
            obfuscatedBaseAuthApiUrl = BuildConfig.SneakBaseAuthApiUrl.hexToByteArray(),
            obfuscatedAuthorizeUrl = BuildConfig.SneakAuthorizeUrl.hexToByteArray(),
        )
    }
}
