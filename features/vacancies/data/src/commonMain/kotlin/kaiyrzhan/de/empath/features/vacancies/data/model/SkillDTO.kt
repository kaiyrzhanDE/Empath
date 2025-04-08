package kaiyrzhan.de.empath.features.vacancies.data.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SkillDTO(
    @SerialName("name") val name: String?,
    @SerialName("id") val id: String,
)

internal fun SkillDTO.toDomain(): Skill{
    return Skill(
        id = id,
        name = name.orEmpty(),
    )
}