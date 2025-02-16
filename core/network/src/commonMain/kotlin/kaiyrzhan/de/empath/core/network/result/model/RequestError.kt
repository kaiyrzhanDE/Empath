package kaiyrzhan.de.empath.core.network.result.model

data class RequestError<out T>(
    val code: Int,
    val message: String,
    val data: T? = null,
)

