package ninja.bryansills.loudping.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ninja.bryansills.loudping.app.theme.primaryAsColorInt
import ninja.bryansills.loudping.app.theme.primaryDarkAsColorInt
import ninja.bryansills.loudping.ui.login.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val launchCustomTab = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_CANCELED && viewModel.progress == LoginProgress.LoggingIn) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.progress == LoginProgress.Initializing) {
            viewModel.progress = LoginProgress.LoggingIn
            launchCustomTab.launch(
                input = createCustomTabIntent(viewModel.loginUrl),
                options = ActivityOptionsCompat.makeCustomAnimation(
                    this,
                    R.anim.enter_from_bottom,
                    R.anim.fade_out,
                ),
            )
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginComplete.collectLatest { finish() }
            }
        }
    }

    private fun createCustomTabIntent(stringUri: String): Intent {
        val builder = CustomTabsIntent.Builder()
        val defaultColorScheme = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(primaryAsColorInt)
            .build()
        val darkColorScheme = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(primaryDarkAsColorInt)
            .build()
        val customTabsIntent = builder
            .setSendToExternalDefaultHandlerEnabled(true)
            .setShowTitle(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .setDefaultColorSchemeParams(defaultColorScheme)
            .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, darkColorScheme)
            .setExitAnimations(this, R.anim.fade_in, R.anim.exit_to_bottom)
            .build()

        return customTabsIntent.intent.apply {
            setData(Uri.parse(stringUri))
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val code = intent.data?.getQueryParameter("code")
        val state = intent.data?.getQueryParameter("state")

        if (code != null && state != null) {
            binding.progress.visibility = View.VISIBLE
            viewModel.setAuthorizationCode(code, state)
        } else {
            Log.wtf("BLARG", "Who is messing with my app?")
        }
    }
}
