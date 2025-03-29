package kaiyrzhan.de.empath.core.utils.result

public const val BASE_URL: String = "http://45.139.78.34/"

private const val FILE_STORAGE_BASE_URL = "${BASE_URL}api/v1/file-storage"

public typealias Url = String

public fun Url?.addBaseUrl(baseUrl: String = FILE_STORAGE_BASE_URL): String? {
    return when {
        isNullOrBlank() -> null
        startsWith("http", ignoreCase = true) -> this
        else -> baseUrl.trimEnd('/') + "/" + this.trimStart('/')
    }
}
