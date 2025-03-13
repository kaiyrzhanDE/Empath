package kaiyrzhan.de.empath.features.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class UserRequest(
    @SerialName("nickname") val nickname: String,
    @SerialName("email") val email: String,
    @SerialName("id") val id: String,
    @SerialName("password") val password: String,
    @SerialName("sex") val sex: String?,
    @SerialName("name") val name: String?,
    @SerialName("lastname") val lastname: String?,
    @SerialName("patronymic") val patronymic: String?,
    @SerialName("dateOfBirth") val dateOfBirth: String?,
    @SerialName("image") val image: String?,
)