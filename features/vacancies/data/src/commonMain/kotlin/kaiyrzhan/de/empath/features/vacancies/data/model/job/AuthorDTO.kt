package kaiyrzhan.de.empath.features.vacancies.data.model.job

import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Author
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class AuthorDTO(
    @SerialName("name") internal val companyName: String?,
    @SerialName("email") internal val email: String?,
    @SerialName("about_us") internal val companyDescription: String?,
)

internal fun AuthorDTO.toDomain(): Author {
    return Author(
        companyName = companyName.orEmpty(),
        companyDescription = companyDescription.orEmpty(),
        email = email.orEmpty(),
    )
}