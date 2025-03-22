package kaiyrzhan.de.empath.core.network.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
public data class TokenEntity(
    @SerialName("accessToken") val accessToken: String? = null,
    @SerialName("refreshToken") val refreshToken: String? = null,
)

public fun TokenEntity.toDomain(): Token = Token(
    accessToken = this.accessToken.orEmpty(),
    refreshToken = this.refreshToken.orEmpty(),
)

public fun Token.toData(): TokenEntity = TokenEntity(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
)

public data class Token(
    val accessToken: String,
    val refreshToken: String,
){
    public fun isAuthorized(): Boolean = accessToken.isNotBlank() && refreshToken.isNotBlank()
}
