package ninja.bryansills.loudping.ui.refreshtokenentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog

fun NavGraphBuilder.refreshTokenEntry(onDismiss: () -> Unit) {
    dialog<RefreshTokenEntry> {
        RefreshTokenEntryScreen(onDismiss = onDismiss)
    }
}

@Composable
fun RefreshTokenEntryScreen(
    onDismiss: () -> Unit,
    viewModel: RefreshTokenEntryViewModel = hiltViewModel(),
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {}) { Text(text = "Submit") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(text = "Cancel") }
        },
        title = { Text(text = "Refresh Token Entry") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = "Please enter a valid refresh token")
                TextField(
                    state = viewModel.input,
                    placeholder = { Text(text = "HAhw0Wn07GR3at...") },
                    lineLimits = TextFieldLineLimits.SingleLine,
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp,
                    ),
                )
            }
        },
    )
}
