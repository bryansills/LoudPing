package ninja.bryansills.loudping.ui.login

import android.app.Activity
import android.content.Intent
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
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ninja.bryansills.loudping.app.theme.primaryAsColorInt
import ninja.bryansills.loudping.app.theme.primaryDarkAsColorInt
import ninja.bryansills.loudping.di.LoudPingViewModelFactory
import ninja.bryansills.loudping.ui.login.databinding.ActivityLoginBinding

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
class LoginActivity(
  private val viewModelFactory: LoudPingViewModelFactory,
) : ComponentActivity() {

  override val defaultViewModelProviderFactory: ViewModelProvider.Factory
    get() = viewModelFactory

  private lateinit var binding: ActivityLoginBinding
  private val viewModel: LoginViewModel by viewModels()

  private val launchCustomTab =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      lifecycleScope.launch {
        binding.loginContent.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE

        // timing here is terrible. give the system a bit of time to call `onNewIntent()`.
        delay(500)

        if (result.resultCode == RESULT_CANCELED && viewModel.progress == LoginProgress.LoggingIn) {
          finish()
        }
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.loginButton.setOnClickListener {
      viewModel.progress = LoginProgress.LoggingIn
      launchCustomTab.launch(
        input = createCustomTabIntent(viewModel.loginUrl),
        options =
          ActivityOptionsCompat.makeCustomAnimation(
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
    val defaultColorScheme =
      CustomTabColorSchemeParams.Builder().setToolbarColor(primaryAsColorInt).build()
    val darkColorScheme =
      CustomTabColorSchemeParams.Builder().setToolbarColor(primaryDarkAsColorInt).build()
    val customTabsIntent =
      builder
        .setSendToExternalDefaultHandlerEnabled(true)
        .setShowTitle(true)
        .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
        .setDefaultColorSchemeParams(defaultColorScheme)
        .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, darkColorScheme)
        .setExitAnimations(this, R.anim.fade_in, R.anim.exit_to_bottom)
        .build()

    return customTabsIntent.intent.apply { data = stringUri.toUri() }
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)

    val code = intent.data?.getQueryParameter("code")
    val state = intent.data?.getQueryParameter("state")

    if (code != null && state != null) {
      binding.loginContent.visibility = View.GONE
      binding.progress.visibility = View.VISIBLE
      viewModel.setAuthorizationCode(code, state)
    } else {
      Log.wtf("BLARG", "Who is messing with my app?")
    }
  }
}
