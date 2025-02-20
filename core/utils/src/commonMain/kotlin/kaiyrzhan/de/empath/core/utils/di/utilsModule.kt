package kaiyrzhan.de.empath.core.utils.di

import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

public val utilsModule: Module = module {
    single { AppDispatchers() }
}
