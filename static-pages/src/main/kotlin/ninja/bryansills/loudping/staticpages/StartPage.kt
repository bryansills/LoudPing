package ninja.bryansills.loudping.staticpages

import kotlinx.html.unsafe

fun StartPage(
    startUrl: String
): String {
    return buildHtml {
        unsafe {
            raw(
                """
<meta http-equiv="refresh" content="0; url=$startUrl" />
    """.trimIndent()
            )
        }
    }
}