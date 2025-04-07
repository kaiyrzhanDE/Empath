package kaiyrzhan.de.empath.core.utils

public inline fun <reified T : Enum<T>> String?.toEnumSafe(default: T): T {
    return enumValues<T>().firstOrNull { type ->
        type.name.equals(this, ignoreCase = true)
    } ?: default
}


public fun Int?.toGroupedString(): String {
    return if (this == null) "N/A"
    else toString()
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
}

public fun Long?.toGroupedString(): String {
    return if (this == null) "N/A"
    else toString()
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
}



