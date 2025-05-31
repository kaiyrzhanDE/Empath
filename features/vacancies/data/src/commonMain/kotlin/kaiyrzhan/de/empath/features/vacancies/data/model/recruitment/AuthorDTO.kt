package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Author
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class AuthorDTO(
    @SerialName("name") internal val name: String?,
    @SerialName("email") internal val email: String?,
)

internal fun AuthorDTO.toDomain(): Author {
    return Author(
        name = name.orEmpty(),
        email = email.orEmpty(),
    )
}
