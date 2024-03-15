package ninja.bryansills.loudping.app.core.login

import android.webkit.JavascriptInterface

class JavascriptCallback(private val onInvoke: (String, String) -> Unit) {
    @JavascriptInterface
    fun onSuccess(state: String, code: String) {
        onInvoke(state, code)
    }
}
