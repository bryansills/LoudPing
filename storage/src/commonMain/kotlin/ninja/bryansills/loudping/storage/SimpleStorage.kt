package ninja.bryansills.loudping.storage

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface SimpleStorage {
  operator fun <Data> get(key: SimpleEntry<Data>): Flow<Data>

  suspend fun <Data> update(key: SimpleEntry<Data>, transform: suspend (t: Data) -> Data?): Data

  suspend fun <Data> clear(key: SimpleEntry<Data>): Boolean

  suspend fun edit(
      transform: suspend (mutablePreferences: MutablePreferences) -> Unit,
  ): Preferences
}

suspend fun <Data> SimpleStorage.first(key: SimpleEntry<Data>): Data {
  return this[key].first()
}
