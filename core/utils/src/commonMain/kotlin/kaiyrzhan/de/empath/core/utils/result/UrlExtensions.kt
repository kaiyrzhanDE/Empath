package kaiyrzhan.de.empath.core.utils.result

private const val FILE_STORAGE_BASE_URL = "${BASE_URL}api/v1/file-storage"

public typealias Url = String

public fun Url.addBase(baseUrl: String = FILE_STORAGE_BASE_URL): String {
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

public fun Url.removeBase(baseUrl: String = FILE_STORAGE_BASE_URL): String {
    return this.let { url ->
        if (url.startsWith(baseUrl)) {
            url.removePrefix(baseUrl).trimStart('/')
        } else {
            url
        }
    }
}
