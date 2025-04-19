package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.features.profile.domain.model.User

internal data class UserUi(
    val nickname: String,
    val lastname: String,
    val name: String,
    val patronymic: String,
    val imageUrl: String?,
) {
    fun getFullName(): String {
        return listOf(lastname, name, patronymic)
            .filter { it.isNotBlank() }
            .joinToString(" ")
    }
}

internal fun User.toUi(): UserUi {
    return UserUi(
        nickname = nickname,
        lastname = lastname,
        name = name,
        patronymic = patronymic,
        imageUrl = imageUrl.addBaseUrl(),
    )
}
