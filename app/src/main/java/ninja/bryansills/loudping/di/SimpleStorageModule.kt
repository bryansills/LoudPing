package ninja.bryansills.loudping.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ninja.bryansills.loudping.storage.RealSimpleStorage
import ninja.bryansills.loudping.storage.SimpleStorage

@InstallIn(SingletonComponent::class)
@Module
interface SimpleStorageModule {
    companion object {
        @Provides
        @Singleton
        fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile("loud-ping-storage")
            }
        }

        @Provides
        @Singleton
        fun providesSimpleStorage(dataStore: DataStore<Preferences>): SimpleStorage {
            return RealSimpleStorage(dataStore)
        }
    }
}
