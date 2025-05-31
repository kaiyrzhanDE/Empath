package kaiyrzhan.de.empath.core.utils.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kaiyrzhan.de.empath.core.utils.AppUtils
import kaiyrzhan.de.empath.core.utils.DesktopAppUtils
import kaiyrzhan.de.empath.core.utils.datastore.DATA_STORE_FILE_NAME
import kaiyrzhan.de.empath.core.utils.datastore.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

public actual val dataStoreModule: Module = module {
    single<DataStore<Preferences>> { createDataStore { DATA_STORE_FILE_NAME } }
    factory<AppUtils> { DesktopAppUtils() }
}
