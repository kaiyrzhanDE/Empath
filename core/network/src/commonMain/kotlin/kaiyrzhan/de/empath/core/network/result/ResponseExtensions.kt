package kaiyrzhan.de.empath.core.network.result

import io.ktor.client.statement.HttpResponse
import kaiyrzhan.de.empath.core.utils.result.StatusCode

public fun HttpResponse.getStatusCode(): StatusCode {
    return StatusCode.entries.find { it.code == status.value } ?: StatusCode.Unknown
}