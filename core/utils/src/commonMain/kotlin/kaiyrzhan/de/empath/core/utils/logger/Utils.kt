package kaiyrzhan.de.empath.core.utils.logger

public fun Any.className(prefix: String = "veildc"): String = "$prefix:${this::class.simpleName.orEmpty()}"
