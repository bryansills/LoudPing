package ninja.bryansills.loudping.ui.refreshtokenentry

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RefreshTokenEntryViewModel @Inject constructor() : ViewModel() {
    val input by mutableStateOf(TextFieldState(initialText = ""))
}
