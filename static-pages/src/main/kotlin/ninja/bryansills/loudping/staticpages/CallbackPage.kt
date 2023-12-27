package ninja.bryansills.loudping.staticpages

import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.svg
import kotlinx.html.title
import kotlinx.html.unsafe
import ninja.bryansills.sneak.Sneak
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun CallbackPage(
    sneak: Sneak,
    saltText: String,
    tokenUrl: String,
    clientId: String,
    clientOther: String,
    redirectUrl: String,
): String {
    val tokenUrlBytes = sneak.obfuscate(tokenUrl)
    val authHeaderValue = toHeaderValue(clientId, clientOther)
    val authHeaderBytes = sneak.obfuscate(authHeaderValue)
    val redirectUrlBytes = sneak.obfuscate(redirectUrl)

    return buildHtml {
        style = "height: 100%"
        head {
            title { +"Signing in..." }
        }
        body {
            style =
                "margin: 0px; height: 100%; display: flex; justify-content: center; align-items: center; background: #212121"
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
                    raw(
                        """
const encoder = new TextEncoder();
const decoder = new TextDecoder();

const encode = (saltBytes, rawText) => {
  const rawBytes = encoder.encode(rawText);
  return rawBytes.map((item, index) => {
    const saltBit = saltBytes[index % saltBytes.length];
    return item ^ saltBit;
  })
};

const decode = (saltBytes, rawBytes) => {
  const deciphered = rawBytes.map((item, index) => {
    const saltBit = saltBytes[index % saltBytes.length];
    return item ^ saltBit;
  })
  return decoder.decode(deciphered);
};

const saltBytes = ${saltText.toJsByteArray()}
const tokenUrlBytes = ${tokenUrlBytes.toJsByteArray()}
const authHeaderBytes = ${authHeaderBytes.toJsByteArray()}
const redirectUrlBytes = ${redirectUrlBytes.toJsByteArray()}

const params = (new URL(document.location)).searchParams;
const authorizationCode = params.get("code");

const url = decode(saltBytes, tokenUrlBytes);
const requestHeaders = {
  "content-type": "application/x-www-form-urlencoded",
  "Authorization": decode(saltBytes, authHeaderBytes)
};
const jsonBody = {
  "grant_type": "authorization_code",
  "code": authorizationCode,
  "redirect_uri": decode(saltBytes, redirectUrlBytes)
};

console.log(url);
console.log(requestHeaders);
console.log(jsonBody);

async function doWork() {
    const response = await fetch(
      url,
      {
        method: "POST",
        headers: requestHeaders,
        body: new URLSearchParams(jsonBody)
      }
    );

    const responseJson = await response.json();

    alert(responseJson);
}
doWork();
                """.trimIndent()
                    )
                }
            }
        }
    }
}

private fun String.toJsByteArray(): String {
    return this.toByteArray().toJsByteArray()
}

private fun ByteArray.toJsByteArray(): String {
    return "new Uint8Array([${this.joinToString()}]);"
}

@OptIn(ExperimentalEncodingApi::class)
private fun toHeaderValue(clientId: String, clientOther: String): String {
    val based = Base64.encode("$clientId:$clientOther".toByteArray())
    return "Basic $based"
}