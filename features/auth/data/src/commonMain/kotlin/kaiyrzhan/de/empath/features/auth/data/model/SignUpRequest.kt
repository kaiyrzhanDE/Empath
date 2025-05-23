package kaiyrzhan.de.empath.features.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SignUpRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("nickname") val nickname: String,
)