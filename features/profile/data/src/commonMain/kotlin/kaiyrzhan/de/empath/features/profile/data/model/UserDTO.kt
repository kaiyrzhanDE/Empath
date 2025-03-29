package kaiyrzhan.de.empath.features.profile.data.model

import kaiyrzhan.de.empath.core.network.utils.toEnumSafe
import kaiyrzhan.de.empath.core.utils.toInstantOrNull
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.model.User.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class UserDTO(
    @SerialName("id") val id: String,
    @SerialName("nickname") val nickname: String?,
    @SerialName("email") val email: String?,
    @SerialName("password") val password: String?,
    @SerialName("lastname") val lastname: String?,
    @SerialName("name") val name: String?,
    @SerialName("patronymic") val patronymic: String?,
    @SerialName("date_birth") val dateOfBirth: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("image") val imageUrl: String?,
)

internal fun UserDTO.toDomain(): User {
    return User(
        id = id,
        nickname = nickname.orEmpty(),
        email = email.orEmpty(),
        password = password.orEmpty(),
        lastname = lastname.orEmpty(),
        name = name.orEmpty(),
        patronymic = patronymic.orEmpty(),
        dateOfBirth = dateOfBirth.toInstantOrNull(),
        gender = gender.toEnumSafe(Gender.OTHER),
        imageUrl = imageUrl,
    )
}