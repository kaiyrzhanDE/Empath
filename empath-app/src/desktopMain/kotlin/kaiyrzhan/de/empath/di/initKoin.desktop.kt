package kaiyrzhan.de.empath.di

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger

internal actual fun defaultKoinLogger(level: Level): Logger {
    return PrintLogger(level)
}
