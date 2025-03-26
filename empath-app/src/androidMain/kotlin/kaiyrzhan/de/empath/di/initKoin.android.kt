package kaiyrzhan.de.empath.di

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.android.logger.AndroidLogger

internal actual fun defaultKoinLogger(level: Level): Logger {
    return AndroidLogger(level)
}