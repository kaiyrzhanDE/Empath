package kaiyrzhan.de.empath.core.utils.di

import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kaiyrzhan.de.empath.core.utils.logger.Logger
import org.koin.core.module.Module
import org.koin.dsl.module

public expect val dataStoreModule: Module

public val utilsModule: Module = module(createdAtStart = true) {
    single<AppDispatchers> { AppDispatchers() }
    factory<BaseLogger> { Logger() }
}



