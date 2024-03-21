package ninja.bryansills.loudping.app.core.login

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
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

//    LightStatusBarForDarkBackgroundDisposableEffect("login")

    SpotifyWebView(
        loginUrl = loginViewModel.loginUrl,
        javascriptCallback = callback,
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxSize()
    )
}

@Composable
fun LightStatusBarForDarkBackgroundDisposableEffect(tag: String) {
    val view: View = LocalView.current
    val insetsController = remember {
        val window: Window = (view.context as Activity).window
        WindowCompat
            .getInsetsController(window, view)
    }

    val isOriginallyLightStatusBar = remember {
        insetsController.isAppearanceLightStatusBars
    }

    SideEffect {
        Log.d("BLARG", "$tag: originally $isOriginallyLightStatusBar")
        insetsController.isAppearanceLightStatusBars = false
    }

    if (isOriginallyLightStatusBar) {
        DisposableEffect(Unit) {

            onDispose {
                Log.d("BLARG", "$tag: setting back to $isOriginallyLightStatusBar")
                insetsController.isAppearanceLightStatusBars = true
            }
        }
    }
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
