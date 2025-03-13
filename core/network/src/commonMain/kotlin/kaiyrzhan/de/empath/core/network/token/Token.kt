package kaiyrzhan.de.empath.core.network.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
internal data class TokenEntity(
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

internal data class Token(
    val accessToken: String,
    val refreshToken: String,
)
