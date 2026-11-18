package ninja.bryansills.loudping.sneak.network

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import ninja.bryansills.sneak.Sneak

@ContributesTo(AppScope::class)
@BindingContainer
object SneakBindingContainer {
  //  @Provides fun provideSneak(): Sneak = Sneak(BuildConfig.SneakSalt.toByteArray())
  @Provides fun provideSneak(): Sneak = Sneak(ByteArray(0))

  //  @OptIn(ExperimentalStdlibApi::class)
  //  @Provides
  //  fun provideNetworkSneak(sneak: Sneak): NetworkSneak =
  //    RealNetworkSneak(
  //      sneak = sneak,
  //      obfuscatedClientId = BuildConfig.SneakClientId.safeHexToByteArray(),
  //      obfuscatedClientSecret = BuildConfig.SneakClientSecret.safeHexToByteArray(),
  //      obfuscatedRedirectUrl = BuildConfig.SneakRedirectUrl.safeHexToByteArray(),
  //      obfuscatedBaseApiUrl = BuildConfig.SneakBaseApiUrl.safeHexToByteArray(),
  //      obfuscatedBaseAuthApiUrl = BuildConfig.SneakBaseAuthApiUrl.safeHexToByteArray(),
  //      obfuscatedAuthorizeUrl = BuildConfig.SneakAuthorizeUrl.safeHexToByteArray(),
  //    )
  @OptIn(ExperimentalStdlibApi::class)
  @Provides
  fun provideNetworkSneak(sneak: Sneak): NetworkSneak =
    RealNetworkSneak(
      sneak = sneak,
      obfuscatedClientId = ByteArray(0),
      obfuscatedClientSecret = ByteArray(0),
      obfuscatedRedirectUrl = ByteArray(0),
      obfuscatedBaseApiUrl = ByteArray(0),
      obfuscatedBaseAuthApiUrl = ByteArray(0),
      obfuscatedAuthorizeUrl = ByteArray(0),
    )
}

private fun String.safeHexToByteArray(): ByteArray {
  return try {
    this.hexToByteArray()
  } catch (_: NumberFormatException) {
    "DEADBEEF".hexToByteArray()
  }
}
