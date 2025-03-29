package kaiyrzhan.de.empath.core.network.token

internal data class RefreshTokenException(
    override val message: String,
) : Throwable(message)
