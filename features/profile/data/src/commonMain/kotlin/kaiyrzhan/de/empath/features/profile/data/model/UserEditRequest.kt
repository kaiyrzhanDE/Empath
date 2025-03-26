package kaiyrzhan.de.empath.features.profile.data.model

import kaiyrzhan.de.empath.core.utils.IsoType
import kaiyrzhan.de.empath.core.utils.toIso
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserEditRequest(
    @SerialName("nickname") val nickname: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("name") val name: String?,
    @SerialName("lastname") val lastname: String?,
    @SerialName("patronymic") val patronymic: String?,
    @SerialName("dateOfBirth") val dateOfBirth: String?,
)

private fun String.orNull(): String? {
    return if (this.isBlank()) null else this
}

internal fun User.toData(): UserEditRequest {
    return UserEditRequest(
        nickname = nickname.orNull(),
        lastname = lastname.orNull(),
        name = name.orNull(),
        patronymic = patronymic.orNull(),
        dateOfBirth = dateOfBirth.toIso(type = IsoType.DATE),
        gender = gender.toString().lowercase(),
    )
}