package kaiyrzhan.de.empath.di

import kaiyrzhan.de.empath.core.utils.di.desktopUtilsModule
import org.koin.core.module.Module

internal actual val platformModules: List<Module> = listOf(
    desktopUtilsModule,
)
