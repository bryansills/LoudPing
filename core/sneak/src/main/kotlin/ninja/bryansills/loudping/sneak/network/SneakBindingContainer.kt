package ninja.bryansills.loudping.sneak.network

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import ninja.bryansills.loudping.sneak.BuildConfig
import ninja.bryansills.sneak.Sneak

@ContributesTo(AppScope::class)
@BindingContainer
object SneakBindingContainer {
  @Provides fun provideSneak(): Sneak = Sneak(BuildConfig.Salt.toByteArray())

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

  @Provides
  @Named("jvm-refresh-token")
  fun provideJvmRefreshToken(): String = BuildConfig.JvmRefreshToken
}

internal fun String.safeHexToByteArray(): ByteArray {
  return try {
    this.hexToByteArray()
  } catch (_: NumberFormatException) {
    "DEADBEEF".hexToByteArray()
  }
}
