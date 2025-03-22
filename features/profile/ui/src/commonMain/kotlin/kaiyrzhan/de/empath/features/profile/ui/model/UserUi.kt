package kaiyrzhan.de.empath.features.profile.ui.model

import kaiyrzhan.de.empath.core.utils.datePattern
import kaiyrzhan.de.empath.core.utils.toDate
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.model.User.Gender

internal data class UserUi(
    val id: String,
    val nickname: String,
    val email: String,
    val password: String,
    val lastname: String,
    val name: String,
    val patronymic: String,
    val dateOfBirth: String,
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
        dateOfBirth = dateOfBirth.toDate(pattern = datePattern),
        gender = gender,
        image = image,
    )
}