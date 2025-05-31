package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@Serializable
internal class AuthorUi(
    val name: String,
    val email: String,
)


internal fun Author.toUi(): AuthorUi {
    return AuthorUi(
        name = name,
        email = email,
    )
}

internal fun AuthorUi.toDomain(): Author {
    return Author(
        name = name,
        email = email,
    )
}