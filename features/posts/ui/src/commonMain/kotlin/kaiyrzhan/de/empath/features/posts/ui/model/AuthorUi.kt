package kaiyrzhan.de.empath.features.posts.ui.model

import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.features.posts.domain.model.Author

internal data class AuthorUi(
    val id: String,
    val nickname: String,
    val imageUrl: String?,
    val fullName: String,
    val rating: Int,
)

internal fun Author.toUi(): AuthorUi {
    return AuthorUi(
        id = id,
        nickname = nickname,
        imageUrl = imageUrl.addBaseUrl(),
        fullName = fullName,
        rating = rating,
    )
}

internal fun AuthorUi.toDomain(): Author {
    return Author(
        id = id,
        nickname = nickname,
        imageUrl = imageUrl.addBaseUrl(),
        fullName = fullName,
        rating = rating,
    )
}
