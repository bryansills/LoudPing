package ninja.bryansills.loudping.app.core.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberSaveableWebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val startTime = rememberSaveable { loginViewModel.now }
    val scope = rememberCoroutineScope()
    val callback = remember(startTime, scope) {
        JavascriptCallback { state, code ->
            scope.launch { loginViewModel.setAuthorizationCode(state, code, startTime) }
        }
    }

    SpotifyWebView(
        loginUrl = loginViewModel.getLoginUrl(startTime),
        javascriptCallback = callback,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun SpotifyWebView(
    loginUrl: String,
    javascriptCallback: JavascriptCallback,
    modifier: Modifier = Modifier
) {
    val navigator = rememberWebViewNavigator()
    val webViewState = rememberSaveableWebViewState()

    LaunchedEffect(navigator, loginUrl) {
        navigator.loadUrl(loginUrl)
    }

    WebView(
        state = webViewState,
        navigator = navigator,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
            webView.addJavascriptInterface(javascriptCallback, "LoudPing")
        },
        modifier = modifier
    )
}
