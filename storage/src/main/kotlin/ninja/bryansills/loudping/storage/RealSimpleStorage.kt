package ninja.bryansills.loudping.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealSimpleStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SimpleStorage {
    override fun <Data> get(key: SimpleEntry<Data>): Flow<Data> {
        return dataStore.data.map {
            it[key.key] ?: key.defaultValue
        }
    }

    override suspend fun <Data> update(
        key: SimpleEntry<Data>,
        transform: suspend (t: Data) -> Data,
    ): Data {
        val editedPrefs = dataStore.edit { mutablePreferences ->
            val oldValue = mutablePreferences[key.key] ?: key.defaultValue
            mutablePreferences[key.key] = transform(oldValue)
        }

        return editedPrefs[key.key] ?: key.defaultValue
    }
}
