package kaiyrzhan.de.empath.core.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

public val currentTimeZone: TimeZone by lazy { TimeZone.currentSystemDefault() }
public val dateTimePattern: DateTimeFormat<LocalDateTime> by lazy {
    LocalDateTime.Format {
        hour()
        char(':')
        minute()
        char(':')
        second()
        char(' ')
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    }
}

public val datePattern: DateTimeFormat<LocalDateTime> by lazy {
    LocalDateTime.Format {
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    }
}

public fun String.toDate(
    timeZone: TimeZone = currentTimeZone,
    pattern: DateTimeFormat<LocalDateTime> = dateTimePattern,
): String {
    return try {
        val instant = Instant.parse(this)
        val localDateTime = instant.toLocalDateTime(timeZone)
        localDateTime.format(pattern)
    } catch (_: Exception) {
        "Invalid date"
    }
}

