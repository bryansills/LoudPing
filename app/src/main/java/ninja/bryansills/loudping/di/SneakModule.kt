package ninja.bryansills.loudping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ninja.bryansills.loudping.BuildConfig
import ninja.bryansills.loudping.sneak.network.NetworkSneak
import ninja.bryansills.loudping.sneak.network.RealNetworkSneak
import ninja.bryansills.sneak.Sneak

@InstallIn(SingletonComponent::class)
@Module
object SneakModule {

    @Provides fun provideSneak(): Sneak = Sneak(BuildConfig.SneakSalt.toByteArray())

    @OptIn(ExperimentalStdlibApi::class)
    @Provides
    fun provideNetworkSneak(sneak: Sneak): NetworkSneak {
        return RealNetworkSneak(
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
