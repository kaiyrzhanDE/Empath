package kaiyrzhan.de.empath.di

import kaiyrzhan.de.empath.core.utils.di.androidUtilsModule
import org.koin.core.module.Module

internal actual val platformModules: List<Module> = listOf(
    androidUtilsModule,
)
