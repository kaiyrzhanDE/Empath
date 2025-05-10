package kaiyrzhan.de.empath.core.utils.result

private const val FILE_STORAGE_BASE_URL = "${BASE_URL}api/v1/file-storage"

public typealias Url = String

private fun Url.addBase(baseUrl: String): String {
    return when {
        isBlank() -> ""
        startsWith("http", ignoreCase = true) -> this
        else -> baseUrl.trimEnd('/') + "/" + this.trimStart('/')
    }
}

public fun Url?.addBaseUrl(baseUrl: String = FILE_STORAGE_BASE_URL): String? {
    return takeIf { url -> url.isNullOrBlank().not() }?.addBase(baseUrl)
}

public fun Url?.removeBaseUrl(baseUrl: String = FILE_STORAGE_BASE_URL): String? {
    return this?.let { url ->
        if (url.startsWith(baseUrl)) {
            url.removePrefix(baseUrl).trimStart('/')
        } else {
            url
        }
    }
}
