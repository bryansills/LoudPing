package ninja.bryansills.loudping.app.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ninja.bryansills.loudping.app.core.theme.LoudPingTheme

@Composable
fun App() {
    LoudPingTheme {
        Box(modifier = Modifier.padding(64.dp)) {
            Text("Hey we out here!")
        }
    }
}