package ninja.bryansills.loudping.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object Home

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = "Hello world!")
    }
}
