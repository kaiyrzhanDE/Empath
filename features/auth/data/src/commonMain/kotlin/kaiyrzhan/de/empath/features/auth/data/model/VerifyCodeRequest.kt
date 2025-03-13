package kaiyrzhan.de.empath.features.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class VerifyCodeRequest(
    @SerialName("email") val email: String,
    @SerialName("code") val code: String,
)