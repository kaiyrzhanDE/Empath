package kaiyrzhan.de.empath.core.network.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public class TokenRefreshException(
    override val cause: Throwable?,
    override val message: String?,
) : Exception(message, cause)

@Serializable
public data class TokenEntity(
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("refresh_token") val refreshToken: String? = null,
)

internal fun TokenEntity.toDomain() = Token(
    accessToken = this.accessToken.orEmpty(),
    refreshToken = this.refreshToken.orEmpty(),
)

internal fun Token.toData() = TokenEntity(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
)

public data class Token(
    val accessToken: String,
    val refreshToken: String,
)
