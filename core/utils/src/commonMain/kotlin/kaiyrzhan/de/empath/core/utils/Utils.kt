package kaiyrzhan.de.empath.core.utils

public inline fun <reified T : Enum<T>> String?.toEnumSafe(default: T): T {
    return enumValues<T>().firstOrNull { type ->
        type.name.equals(this, ignoreCase = true)
    } ?: default
}

public inline fun <reified T : Enum<T>> String?.toEnumSafe(
    default: T,
    crossinline match: (enum: T, value: String?) -> Boolean,
): T {
    return enumValues<T>().firstOrNull { enum -> match(enum, this) } ?: default
}




public fun Int?.toGroupedString(): String {
    return if (this == null) "_"
    else toString()
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
}

public fun Long?.toGroupedString(): String {
    return if (this == null) "_"
    else toString()
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
}



public inline fun String.toIntLimited(): Int? {
    if (isEmpty() || any { !it.isDigit() }) return null

    val longValue = toLongOrNull() ?: return null
    return if (longValue > Int.MAX_VALUE) Int.MAX_VALUE else longValue.toInt()
}




