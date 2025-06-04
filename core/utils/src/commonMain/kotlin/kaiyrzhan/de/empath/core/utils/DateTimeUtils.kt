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

public enum class DatePattern {
    DATE,
    DATE_TIME,
}

public fun String?.toInstantOrNull(
    pattern: DatePattern = DatePattern.DATE_TIME,
): Instant? {
    return try {
        this?.takeIf { it.isNotBlank() }?.let { dateTime ->
            when (pattern) {
                DatePattern.DATE_TIME -> {
                    val dotIndex = dateTime.indexOf('.')
                    val cleaned = if (dotIndex != -1) {
                        val prefix = dateTime.substring(0, dotIndex)
                        val fraction = dateTime.substring(dotIndex + 1)
                            .take(3)
                            .padEnd(3, '0')
                        "$prefix.$fraction" + "Z"
                    } else {
                        dateTime + "Z"
                    }

                    Instant.parse(cleaned)
                }

                DatePattern.DATE -> {
                    val localDate = LocalDate.parse(dateTime)
                    localDate.atStartOfDayIn(TimeZone.UTC)
                }
            }
        }
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
    timeZone: TimeZone = currentTimeZone,
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

public fun LocalDateTime?.dateFormat(
    dateFormat: DateTimeFormat<LocalDateTime> = datePattern,
): String {
    return this?.format(dateFormat).orEmpty()
}

public fun Instant?.toLocalDateTime(): LocalDateTime? {
    return try {
        this?.toLocalDateTime(currentTimeZone)
    } catch (_: Exception) {
        null
    }
}

public enum class IsoType {
    DATE_TIME,
    DATE,
    TIME,
}

public fun Instant?.toIso(type: IsoType = IsoType.DATE_TIME): String? {
    return try {
        val localDateTime = this?.toLocalDateTime(TimeZone.UTC)
        when (type) {
            IsoType.DATE_TIME -> localDateTime?.toString()
            IsoType.DATE -> localDateTime?.date?.toString()
            IsoType.TIME -> localDateTime?.time?.toString()
        }
    } catch (_: Exception) {
        null
    }
}

