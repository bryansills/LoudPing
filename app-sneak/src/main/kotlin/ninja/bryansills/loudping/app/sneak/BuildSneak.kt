package ninja.bryansills.loudping.app.sneak

interface BuildSneak {
    val clientId: String

    val clientSecret: String

    val redirectUrl: String

    val baseApiUrl: String

    val baseAuthApiUrl: String

    val authorizeUrl: String
}
