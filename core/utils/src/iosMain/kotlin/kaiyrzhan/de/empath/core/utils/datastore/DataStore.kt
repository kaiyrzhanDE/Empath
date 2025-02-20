package kaiyrzhan.de.empath.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public class DataStore(): KoinComponent {

    private val prefs: DataStore<Preferences> by inject()

    public fun getPreferences(): DataStore<Preferences> = prefs

}
