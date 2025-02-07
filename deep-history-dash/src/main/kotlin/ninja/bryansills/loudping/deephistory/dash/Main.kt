package ninja.bryansills.loudping.deephistory.dash

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jakewharton.mosaic.runMosaicBlocking
import com.jakewharton.mosaic.ui.Color
import com.jakewharton.mosaic.ui.Text
import kotlinx.coroutines.delay

fun main() = runMosaicBlocking {
    var message by remember { mutableStateOf("haha buttts") }
    LaunchedEffect(Unit) {
        delay(2000)
        message = "we in the middle"
        delay(3000)
        message = "we done now"
    }

    Text(value = message, color = Color.Yellow)
}
