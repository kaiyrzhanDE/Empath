package kaiyrzhan.de.empath.core.network.utils

import kaiyrzhan.de.empath.core.network.BASE_URL

public fun String.addBaseUrl(baseUrl: String = BASE_URL): String {
    return if (this.startsWith("http", ignoreCase = true)) this
    else baseUrl.trimEnd('/') + "/" + this.trimStart('/')
}

public inline fun <reified T : Enum<T>> String?.toEnumSafe(default: T): T {
    return enumValues<T>().firstOrNull { type ->
        type.name.equals(this, ignoreCase = true)
    } ?: default
}



