package ninja.bryansills.loudping.app.core.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberSaveableWebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ninja.bryansills.loudping.session.Session

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLogin: (Session) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val rememberedOnLogin by rememberUpdatedState(onLogin)
    val callback = remember {
        JavascriptCallback { state, code ->
            scope.launch {
                val refreshToken = loginViewModel.setAuthorizationCode(state, code)
                withContext(Dispatchers.Main) {
                    rememberedOnLogin(Session.LoggedIn(UUID.randomUUID()))
                }
            }
        }
    }

    SpotifyWebView(
        loginUrl = loginViewModel.loginUrl,
        javascriptCallback = callback,
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxSize()
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
        Log.d("BLARG", loginUrl)
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
