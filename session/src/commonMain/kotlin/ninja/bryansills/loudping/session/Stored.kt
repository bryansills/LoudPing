package ninja.bryansills.loudping.session

import androidx.datastore.preferences.core.stringPreferencesKey
import ninja.bryansills.loudping.storage.SimpleEntry

object Stored {
  val RefreshToken =
      SimpleEntry(
          stringPreferencesKey("refresh-token"),
          "",
      )
  val AccessToken =
      SimpleEntry(
          stringPreferencesKey("access-token"),
          "",
      )
  val AccessTokenExpiresAt =
      SimpleEntry(
          stringPreferencesKey("access-token-expires-at"),
          "2000-01-01T09:00:00Z",
      )
}
