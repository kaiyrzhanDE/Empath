package kaiyrzhan.de.empath.core.utils.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

public object DataStoreKeys {
    public val USER_AUTH_ACCESS_TOKEN: Preferences.Key<String> = stringPreferencesKey("user_auth_access_token")
    public val USER_AUTH_REFRESH_TOKEN: Preferences.Key<String> = stringPreferencesKey("user_auth_refresh_token")
}
