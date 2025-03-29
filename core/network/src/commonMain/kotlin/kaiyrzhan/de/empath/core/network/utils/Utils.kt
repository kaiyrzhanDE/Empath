package kaiyrzhan.de.empath.core.network.utils

import kaiyrzhan.de.empath.core.network.BASE_URL

private const val FILE_STORAGE_BASE_URL = "${BASE_URL}api/v1/file-storage"

public typealias Url = String

public fun Url?.addBaseUrl(baseUrl: String = FILE_STORAGE_BASE_URL): String? {
    return when {
        isNullOrBlank() -> null
        startsWith("http", ignoreCase = true) -> this
        else -> baseUrl.trimEnd('/') + "/" + this.trimStart('/')
    }
}

public inline fun <reified T : Enum<T>> String?.toEnumSafe(default: T): T {
    return enumValues<T>().firstOrNull { type ->
        type.name.equals(this, ignoreCase = true)
    } ?: default
}




