package ninja.bryansills.loudping.sneak.network

interface NetworkSneak {
    val clientId: String

    val clientSecret: String

    val redirectUrl: String

    val baseApiUrl: String

    val baseAuthApiUrl: String

    val authorizeUrl: String
}
