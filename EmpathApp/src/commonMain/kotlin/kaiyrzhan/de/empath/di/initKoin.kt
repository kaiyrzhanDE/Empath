package kaiyrzhan.de.empath.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.dsl.KoinAppDeclaration

internal expect fun defaultKoinLogger(level: Level): Logger

public fun KoinApplication.initKoin(
    isDebug: Boolean,
    logLevel: Level = Level.INFO,
) {
    allowOverride(false)
    if(isDebug){
        logger(defaultKoinLogger(logLevel))
    }

    modules(commonModules + platformModules)
}

public fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            commonModules + platformModules
        )
    }
}
