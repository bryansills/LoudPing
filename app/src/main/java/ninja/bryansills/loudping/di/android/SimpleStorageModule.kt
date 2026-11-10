package ninja.bryansills.loudping.di.android

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ninja.bryansills.loudping.storage.RealSimpleStorage
import ninja.bryansills.loudping.storage.SimpleStorage

@ContributesTo(AppScope::class)
@BindingContainer
interface SimpleStorageModule {
  companion object {
    @SingleIn(AppScope::class)
    @Provides
    fun providesDataStore(context: Context): DataStore<Preferences> =
      PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("loud-ping-storage")
      }

    @SingleIn(AppScope::class)
    @Provides
    fun providesSimpleStorage(dataStore: DataStore<Preferences>): SimpleStorage =
      RealSimpleStorage(dataStore)
  }
}
