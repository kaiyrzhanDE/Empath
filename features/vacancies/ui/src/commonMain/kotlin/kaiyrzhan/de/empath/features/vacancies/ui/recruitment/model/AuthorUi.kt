package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@JvmInline
@Serializable
internal value class AuthorUi(
    val name: String,
)


internal fun Author.toUi(): AuthorUi {
    return AuthorUi(
        name = name,
    )
}