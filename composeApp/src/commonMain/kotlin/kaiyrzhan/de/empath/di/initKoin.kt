package kaiyrzhan.de.empath.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

public fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(commonModules + platformModules)
    }
}
