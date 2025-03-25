package kaiyrzhan.de.empath.features.profile.ui.model

import kaiyrzhan.de.empath.core.utils.toLocalDateTime
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.model.User.Gender
import kotlinx.datetime.LocalDateTime

internal data class UserUi(
    val id: String,
    val nickname: String,
    val email: String,
    val password: String,
    val lastname: String,
    val name: String,
    val patronymic: String,
    val dateOfBirth: LocalDateTime?,
    val gender: Gender,
    val image: String,
)

internal fun User.toUi(): UserUi {
    return UserUi(
        id = id,
        nickname = nickname,
        email = email,
        password = password,
        lastname = lastname,
        name = name,
        patronymic = patronymic,
        dateOfBirth = dateOfBirth.toLocalDateTime(),
        gender = gender,
        image = image,
    )
}