package ninja.bryansills.loudping.session

import androidx.datastore.preferences.core.stringPreferencesKey
import ninja.bryansills.loudping.storage.SimpleEntry

object Stored {
    val RefreshToken = SimpleEntry(
        stringPreferencesKey("refresh-token"),
        "",
    )
}
