package kaiyrzhan.de.empath.core.network.token

internal data class AuthenticationException(
    override val message: String,
) : Exception(message)


internal data class RequestException(
    override val message: String,
) : Throwable(message)
