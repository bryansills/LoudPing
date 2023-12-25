package ninja.bryansills.loudping.staticpages

import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.svg
import kotlinx.html.title
import kotlinx.html.unsafe

fun CallbackPage(): String = buildHtml {
    style = "height: 100%"
    head {
        title { +"Signing in..." }
    }
    body {
        style = "margin: 0px; height: 100%; display: flex; justify-content: center; align-items: center; background: #212121"
        svg {
            attributes["width"] = "100px"
            attributes["height"] = "100px"
            attributes["viewBox"] = "0 0 40 40"
            unsafe {
                raw(
                    """
<path opacity="0.2" fill="#FFF" d="M20.201,5.169c-8.254,0-14.946,6.692-14.946,14.946c0,8.255,6.692,14.946,14.946,14.946
    s14.946-6.691,14.946-14.946C35.146,11.861,28.455,5.169,20.201,5.169z M20.201,31.749c-6.425,0-11.634-5.208-11.634-11.634
    c0-6.425,5.209-11.634,11.634-11.634c6.425,0,11.633,5.209,11.633,11.634C31.834,26.541,26.626,31.749,20.201,31.749z"/>
<path opacity="0.9" fill="#FFF" d="M26.013,10.047l1.654-2.866c-2.198-1.272-4.743-2.012-7.466-2.012h0v3.312h0
    C22.32,8.481,24.301,9.057,26.013,10.047z">
    <animateTransform attributeType="xml"
        attributeName="transform"
        type="rotate"
        from="0 20 20"
        to="360 20 20"
        dur="0.8s"
        repeatCount="indefinite"/>
</path>
                                """.trimIndent()
                )
            }
        }
        script {
            unsafe {
                raw("""
async function doWork() {
    let url = 'https://api.github.com/repos/javascript-tutorial/en.javascript.info/commits';
    let response = await fetch(url);

    let commits = await response.json();

    alert(commits[0].author.login);
}
doWork();
                """.trimIndent())
            }
        }
    }
}