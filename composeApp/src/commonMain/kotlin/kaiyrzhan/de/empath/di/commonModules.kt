package kaiyrzhan.de.empath.di

import kaiyrzhan.de.empath.core.utils.di.utilsModule
import org.koin.core.module.Module

internal expect val platformModules: List<Module>

internal val commonModules: List<Module> = listOf(
    utilsModule,
)



