package kaiyrzhan.de.empath.core.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal const val KTOR_CLIENT = "KtorClient"
internal const val TIME_OUT = 30_000L

internal fun defaultHttpClient(
    json: Json = DefaultJson,
    block: HttpClientConfig<*>.() -> Unit = {},
) = HttpClient {

    install(DefaultRequest) {
        header(HttpHeaders.AcceptLanguage, "ru")
        header("Locale", "ru")
    }

    defaultRequest {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
        url {
            protocol = URLProtocol.HTTPS
        }
    }

    install(ContentNegotiation) {
        json(json)
    }
    block()

    install(HttpTimeout) {
        connectTimeoutMillis = TIME_OUT
        requestTimeoutMillis = TIME_OUT
    }

}
