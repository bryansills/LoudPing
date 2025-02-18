package ninja.bryansills.loudping.sneak.network

import ninja.bryansills.sneak.Sneak

class RealNetworkSneak(
    private val sneak: Sneak,
    private val obfuscatedClientId: ByteArray,
    private val obfuscatedClientSecret: ByteArray,
    private val obfuscatedRedirectUrl: ByteArray,
    private val obfuscatedBaseApiUrl: ByteArray,
    private val obfuscatedBaseAuthApiUrl: ByteArray,
    private val obfuscatedAuthorizeUrl: ByteArray,
) : NetworkSneak {
    override val clientId: String
        get() = sneak.deobfuscate(obfuscatedClientId)

    override val clientSecret: String
        get() = sneak.deobfuscate(obfuscatedClientSecret)

    override val redirectUrl: String
        get() = sneak.deobfuscate(obfuscatedRedirectUrl)

    override val baseApiUrl: String
        get() = sneak.deobfuscate(obfuscatedBaseApiUrl)

    override val baseAuthApiUrl: String
        get() = sneak.deobfuscate(obfuscatedBaseAuthApiUrl)

    override val authorizeUrl: String
        get() = sneak.deobfuscate(obfuscatedAuthorizeUrl)
}
