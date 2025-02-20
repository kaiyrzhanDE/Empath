package kaiyrzhan.de.empath.core.utils.di

import kaiyrzhan.de.empath.core.utils.datastore.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

public val androidUtilsModule: Module = module(createdAtStart = true) {
    single { createDataStore(context = get()) }
}
