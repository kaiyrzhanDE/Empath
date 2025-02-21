package kaiyrzhan.de.empath.core.utils.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kaiyrzhan.de.empath.core.utils.datastore.DATA_STORE_FILE_NAME
import kaiyrzhan.de.empath.core.utils.datastore.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

public actual val dataStoreModule: Module = module {
    @OptIn(ExperimentalForeignApi::class)
    single<DataStore<Preferences>> {
        createDataStore {
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
        }
    }
}
