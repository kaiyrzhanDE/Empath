package kaiyrzhan.de.empath.core.utils.di

import kaiyrzhan.de.empath.core.utils.datastore.DATA_STORE_FILE_NAME
import kaiyrzhan.de.empath.core.utils.datastore.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

public val desktopUtilsModule: Module = module {
    single { createDataStore { DATA_STORE_FILE_NAME } }
}
