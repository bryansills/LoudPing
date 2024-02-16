package ninja.bryansills.loudping.storage

import androidx.datastore.preferences.core.Preferences

data class SimpleEntry<Data>(
    val key: Preferences.Key<Data>,
    val defaultValue: Data,
)
