package kaiyrzhan.de.empath.core.utils

public inline fun <reified T : Enum<T>> String?.toEnumSafe(default: T): T {
    return enumValues<T>().firstOrNull { type ->
        type.name.equals(this, ignoreCase = true)
    } ?: default
}




