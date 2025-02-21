package kaiyrzhan.de.empath.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import okio.Path.Companion.toPath

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"

internal fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() },
    )
}

public object DataStoreKeys {
    public val USER_AUTH_ACCESS_TOKEN: Preferences.Key<String> = stringPreferencesKey("user_auth_access_token")
    public val USER_AUTH_REFRESH_TOKEN: Preferences.Key<String> = stringPreferencesKey("user_auth_refresh_token")
}


