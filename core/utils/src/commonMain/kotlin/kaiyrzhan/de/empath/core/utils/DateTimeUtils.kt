package kaiyrzhan.de.empath.core.utils

import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

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

public fun String?.toInstantOrNull(): Instant? {
    return try {
        this?.takeIf { dateTime -> dateTime.isNotBlank() }
            ?.let { Instant.parse(it) }
    } catch (_: Exception) {
        null
    }
}

public fun Long.toInstant(): Instant? {
    return try {
        Instant.fromEpochMilliseconds(this)
    } catch (_: Exception) {
        null
    }
}

public fun Long.toLocalDateTime(
    timeZone: TimeZone = currentTimeZone
): LocalDateTime? {
    return try {
        Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
    } catch (_: Exception) {
        null
    }
}

public fun LocalDateTime?.toInstantSafe(timeZone: TimeZone = currentTimeZone): Instant? {
    return try {
        this?.toInstant(timeZone)
    } catch (_: Exception) {
        null
    }
}

public fun LocalDateTime?.toLong(): Long? {
    return this?.toInstantSafe()?.toEpochMilliseconds()
}

public fun LocalDateTime?.format(): String {
    return this?.format(datePattern).orEmpty()
}

public fun Instant?.toLocalDateTime(): LocalDateTime? {
    return try {
        this?.toLocalDateTime(currentTimeZone)
    } catch (_: Exception) {
        null
    }
}

public fun Instant?.toIso(): String? {
    return try {
        this?.toString()
    } catch (_: Exception) {
        "Invalid date"
    }
}
