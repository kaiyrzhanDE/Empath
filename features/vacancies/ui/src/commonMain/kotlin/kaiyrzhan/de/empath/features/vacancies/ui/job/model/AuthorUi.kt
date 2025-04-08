package kaiyrzhan.de.empath.features.vacancies.ui.job.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Author
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthorUi(
    val companyName: String,
    val companyDescription: String,
    val email: String,
)

internal fun Author.toUi(): AuthorUi {
    return AuthorUi(
        companyName = companyName,
        companyDescription = companyDescription,
        email = email,
    )
}