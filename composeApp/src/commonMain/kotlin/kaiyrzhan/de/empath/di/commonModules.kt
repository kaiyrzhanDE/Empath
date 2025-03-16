package kaiyrzhan.de.empath.di

import kaiyrzhan.de.empath.core.network.di.networkModule
import kaiyrzhan.de.empath.core.network.di.tokenModule
import kaiyrzhan.de.empath.core.utils.di.dataStoreModule
import kaiyrzhan.de.empath.core.utils.di.utilsModule
import kaiyrzhan.de.empath.features.auth.data.di.authDataModule
import kaiyrzhan.de.empath.features.auth.domain.di.authDomainModule
import org.koin.core.module.Module

internal expect val platformModules: List<Module>

internal val commonModules: List<Module> = listOf(
    utilsModule,
    dataStoreModule,
    networkModule,
    tokenModule,

    authDomainModule,
    authDataModule,
)



