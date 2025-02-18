package ninja.bryansills.loudping.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealSimpleStorage(
    private val dataStore: DataStore<Preferences>,
) : SimpleStorage {
  override fun <Data> get(key: SimpleEntry<Data>): Flow<Data> {
    return dataStore.data.map { it[key.key] ?: key.defaultValue }
  }

  override suspend fun <Data> update(
      key: SimpleEntry<Data>,
      transform: suspend (t: Data) -> Data?,
  ): Data {
    val editedPrefs =
        dataStore.edit { mutablePreferences ->
          val oldValue = mutablePreferences[key.key] ?: key.defaultValue
          val newValue = transform(oldValue)

          if (newValue != null) {
            mutablePreferences[key.key] = newValue
          } else {
            mutablePreferences.remove(key.key)
          }
        }

    return editedPrefs[key.key] ?: key.defaultValue
  }

  override suspend fun <Data> clear(key: SimpleEntry<Data>): Boolean {
    var didRemove = false

    dataStore.edit { mutablePreferences ->
      didRemove = mutablePreferences[key.key] != null

      mutablePreferences.remove(key.key)
    }

    return didRemove
  }

  override suspend fun edit(
      transform: suspend (mutablePreferences: MutablePreferences) -> Unit,
  ): Preferences {
    return dataStore.edit(transform)
  }
}
