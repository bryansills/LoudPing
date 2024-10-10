package ninja.bryansills.loudping.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityOptionsCompat
import dagger.hilt.android.AndroidEntryPoint
import ninja.bryansills.loudping.app.theme.primaryAsColorInt
import ninja.bryansills.loudping.app.theme.primaryDarkAsColorInt
import ninja.bryansills.loudping.ui.login.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val launchCustomTab = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_CANCELED) {
            Log.d("BLARG", "login canceled")
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchCustomTab.launch(
            input = createCustomTabIntent(viewModel.loginUrl),
            options = ActivityOptionsCompat.makeCustomAnimation(
                this, R.anim.enter_from_bottom, R.anim.fade_out,
            )
        )
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
}
