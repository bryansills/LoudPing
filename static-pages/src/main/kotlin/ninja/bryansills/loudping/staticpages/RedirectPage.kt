package ninja.bryansills.loudping.staticpages

import kotlinx.html.unsafe

fun RedirectPage() = buildHtml {
    unsafe { raw("""
        <meta http-equiv="refresh" content="0; url=https://bungie.net/" />
    """.trimIndent()) }
}