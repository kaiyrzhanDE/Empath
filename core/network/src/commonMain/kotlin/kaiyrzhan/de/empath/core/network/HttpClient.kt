package kaiyrzhan.de.empath.core.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kotlinx.serialization.json.Json

internal const val KTOR_CLIENT = "KtorClient"
internal const val TIME_OUT = 60_000L

internal val DefaultJson = Json {
    encodeDefaults = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    allowStructuredMapKeys = true
    prettyPrint = false
    useArrayPolymorphism = false
    ignoreUnknownKeys = true
}

internal fun defaultHttpClient(
    json: Json = DefaultJson,
    logger: BaseLogger,
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
    install(Logging) {
        this.logger = object : Logger {
            override fun log(message: String) {
                logger.d(KTOR_CLIENT, message)
            }
        }
        level = LogLevel.ALL
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
