package ninja.bryansills.loudping.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface SimpleStorage {
    operator fun <Data> get(key: SimpleEntry<Data>): Flow<Data>

    suspend fun <Data> update(key: SimpleEntry<Data>, transform: suspend (t: Data) -> Data): Data
}

suspend fun <Data> SimpleStorage.first(key: SimpleEntry<Data>): Data {
    return this[key].first()
}
