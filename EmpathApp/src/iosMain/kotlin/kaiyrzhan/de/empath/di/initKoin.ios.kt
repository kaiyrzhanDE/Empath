package kaiyrzhan.de.empath.di

import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger
import platform.Foundation.NSProcessInfo

internal actual fun defaultKoinLogger(level: Level): Logger {
    return PrintLogger(level)
}

private fun isDebugBuild(): Boolean {
    return NSProcessInfo.processInfo.environment["DEBUG"] == "1"
}

public fun initKoin() {
    startKoin {
        initKoin(
            isDebug = isDebugBuild(),
        )
    }
}