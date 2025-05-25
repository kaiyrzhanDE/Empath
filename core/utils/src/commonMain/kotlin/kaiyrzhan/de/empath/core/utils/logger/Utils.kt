package kaiyrzhan.de.empath.core.utils.logger

public fun Any.className(prefix: String = "Empath"): String =
    "$prefix:${this::class.simpleName.orEmpty()}"

public inline fun <R> R?.ifNull(defaultValue: () -> R): R {
    return this ?: defaultValue()
}