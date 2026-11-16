package ninja.bryansills.loudping.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import ninja.bryansills.loudping.BuildConfig
import ninja.bryansills.loudping.sneak.network.NetworkSneak
import ninja.bryansills.loudping.sneak.network.RealNetworkSneak
import ninja.bryansills.sneak.Sneak

@ContributesTo(AppScope::class)
@BindingContainer
object SneakModule {

  @Provides fun provideSneak(): Sneak = Sneak(BuildConfig.SneakSalt.toByteArray())

  @OptIn(ExperimentalStdlibApi::class)
  @Provides
  fun provideNetworkSneak(sneak: Sneak): NetworkSneak =
    RealNetworkSneak(
      sneak = sneak,
      obfuscatedClientId = BuildConfig.SneakClientId.safeHexToByteArray(),
      obfuscatedClientSecret = BuildConfig.SneakClientSecret.safeHexToByteArray(),
      obfuscatedRedirectUrl = BuildConfig.SneakRedirectUrl.safeHexToByteArray(),
      obfuscatedBaseApiUrl = BuildConfig.SneakBaseApiUrl.safeHexToByteArray(),
      obfuscatedBaseAuthApiUrl = BuildConfig.SneakBaseAuthApiUrl.safeHexToByteArray(),
      obfuscatedAuthorizeUrl = BuildConfig.SneakAuthorizeUrl.safeHexToByteArray(),
    )
}

private fun String.safeHexToByteArray(): ByteArray {
  return try {
    this.hexToByteArray()
  } catch (_: NumberFormatException) {
    "DEADBEEF".hexToByteArray()
  }
}
